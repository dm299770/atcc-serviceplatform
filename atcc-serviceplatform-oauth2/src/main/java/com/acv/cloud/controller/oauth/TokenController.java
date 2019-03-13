package com.acv.cloud.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.OauthConstants;
import com.acv.cloud.frame.util.JwtTokenUtil;
import com.acv.cloud.frame.util.ResponseUtil;
import com.acv.cloud.repository.redistemplate.IUserDao;
import com.acv.cloud.services.oauth.OAuthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class TokenController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private IUserDao userDao;

    @RequestMapping("/access")
    public ModelAndView access(Model model, HttpServletRequest request) {
        //map.put("location","http://"+request.getContextPath()+":"+request.getServerPort()+"/");
        ModelAndView modelAndView = new ModelAndView("accesstoken");
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        modelAndView.addObject("requestModel",request);
        return modelAndView;
    }

    @RequestMapping("/accessToken")
    public Object token(HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

        try {
            logger.info("时间戳：{}---> client_id:{}，client_secret:{},code:{},grant_type:{},scope:{},redirect_uri:{}",
                    request.getParameter("st"),
                    request.getParameter("client_id"),
                    request.getParameter("client_secret"),
                    request.getParameter("code"),
                    request.getParameter("grant_type"),
                    request.getParameter("scope"),
                    request.getParameter("redirect_uri"));

            //构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);



            //权限范围
            Set<String> scopes = oauthRequest.getScopes();
            //第三方重定向地址
            String redirect_uri = oauthRequest.getRedirectURI();
            //第三方平台id
            String client_id = oauthRequest.getClientId();
            //第三方平台密码
            String client_secret = oauthRequest.getClientSecret();

            String scope = null;
            try {
                Map<String, Object> checkOAuthThridparty = oAuthService.checkOAuthThridparty(client_id, client_secret);
                //检查提交的客户端id是否正确和检查客户端安全KEY是否正确
                if (checkOAuthThridparty == null) {
                    return ResponseUtil.getAbnormalResponse(client_id, request.getParameter("grant_type"), OauthConstants.INVALID_CLIENT_ID_SECRET);
                } else {
                    scope = (String) checkOAuthThridparty.get("scope");
                    redirect_uri = (String) checkOAuthThridparty.get("redirect_uri");
                    if ("".equals(oauthRequest.getParam(OAuth.OAUTH_SCOPE)) || oauthRequest.getParam(OAuth.OAUTH_SCOPE) == null) {
                        //什么都不做
                    } else {
                        //if (!scope.equals(oauthRequest.getParam(OAuth.OAUTH_SCOPE))) {
                        if(!scopes.contains(scope)){
                            return ResponseUtil.getAbnormalResponse(client_id, request.getParameter("grant_type"), OauthConstants.INVALID_SCOPE);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("{}<{}>{}{}",
                        new Object[]{oauthRequest.getClientId(), request.getParameter("grant_type"), "checkOAuthThridparty查询传入的客户端id和secret异常:", e});
                return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("grant_type"), OauthConstants.SERVER_EXCEPTION);
            }

            String refreshToken = null;
            String accessToken = null;
            //当前时间
            long currentTime = System.currentTimeMillis() / 1000;
            //授权令牌过期时间
            long accessToken_expireIn = currentTime + OauthConstants.ACCESS_TOKEN_EXPIREIN;
            //更新令牌过期时间
            long refreshToken_expireIn = currentTime + OauthConstants.REFRESH_TOKEN_EXPIREIN;
            //授权码模式AUTHORIZATION_CODE
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                //校验redirect_uri(暂时关闭redirect_uri第三方验证)
//                if(!redirect_uri.equals(oauthRequest.getRedirectURI())){
//                    logger.info("redriectURI is error");
//                	return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("grant_type"), OauthConstants.INVALID_PARAMETER);
//                }
                //校验redirect_uri(校验起始uri)
                if(!redirect_uri.startsWith(oauthRequest.getRedirectURI())){
                    logger.info("redriectURI is error");
                  	return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("grant_type"), OauthConstants.INVALID_PARAMETER);
                }


                String authCode = String.format(OauthConstants.PROTOCOL+":%s:%s",client_id,oauthRequest.getParam(OAuth.OAUTH_CODE));
                //String app_account_id = RedisUtils.get(RedisConnection.getShardedJedis(), authCode);

                String app_account_id = userDao.getPhoneNumByAuthCode(authCode);
                //String app_account_id = "2807";
                if (app_account_id == null) {
                    logger.info("<{}>获取令牌，无效的authCode:{}", client_id, authCode);
                    return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("grant_type"), OauthConstants.INVALID_AUTH_CODE);
                }
                //移除code,授权码只能用一次
                //RedisUtils.remove(RedisConnection.getShardedJedis(), authCode);
                userDao.deleteAuthCode(authCode);

                //生成refresh Token
                OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                refreshToken = oauthIssuerImpl.refreshToken();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("REFRESHTOKEN", refreshToken);
                map.put("CLIENT_ID", oauthRequest.getClientId());
                map.put("APP_ACCOUNT_ID", app_account_id);
                map.put("EXPIREIN", String.valueOf(refreshToken_expireIn));
                try {
                    oAuthService.addRefreshToken(map);
                } catch (Exception e) {
                    logger.error("{}<{}>{}{}",
                            new Object[]{oauthRequest.getClientId(), request.getParameter("grant_type"), "addRefreshToken添加刷新token异常:", e});
                    return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("grant_type"), OauthConstants.SERVER_EXCEPTION);
                }
                Map<String, Object> params = new HashMap<String, Object>();
                //获取过期时间
                params.put("EXPIREIN", accessToken_expireIn);
                params.put("SCOPE", scope);
                params.put("APP_ACCOUNT_ID", app_account_id);
                //生成access Token
                accessToken = JwtTokenUtil.generateToken(params);
            } else
                //客户端模式CLIENT_CREDENTIALS
                if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.CLIENT_CREDENTIALS.toString())) {
                    //奇瑞分配的第三方应用ID
                    String app_id = oauthRequest.getParam("app_id");
                    Map<String, Object> params = new HashMap<String, Object>();
                    //获取过期时间
                    params.put("EXPIREIN", accessToken_expireIn);
                    if ("map_application".equals(app_id)) {
                        scope = "scp02001";
                        params.put("APP_ACCOUNT_ID", "auto_accountId");
                    } else if ("voice_application".equals(app_id)) {
                        scope = "scp01003";
                        params.put("APP_ACCOUNT_ID", "baidu_accountId");
                    } else if ("neusoft_hu".equals(app_id)) {
                        scope = "scp02001";
                        params.put("APP_ACCOUNT_ID", "neusoft_accountId");
                    }
                    params.put("SCOPE", scope);
                    //生成access Token
                    accessToken = JwtTokenUtil.generateToken(params);
                    //生成OAuth响应
                    OAuthResponse response = OAuthASResponse
                            .tokenResponse(HttpServletResponse.SC_OK)
                            .setAccessToken(accessToken)
                            .setExpiresIn(String.valueOf(OauthConstants.ACCESS_TOKEN_EXPIREIN))
                            .buildJSONMessage();

                    logger.info("{}<{}>获取令牌，应答信息:{}", client_id, request.getParameter("grant_type"), response.getBody());
                    JSONObject json = JSONObject.parseObject(response.getBody());
                    return json;
                } else

                    //更新令牌 REFRESH_TOKEN
                    if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
                        String refresh_token = oauthRequest.getParam(OAuth.OAUTH_REFRESH_TOKEN);
                        //判断refresh_token有效性(用于刷新access_token)
                        try {
                            if (oAuthService.checkRefreshToken(refresh_token)) {
                                logger.info("<{}>刷新访问令牌，无效的refresh_token:{}", client_id, refresh_token);
                                return ResponseUtil.getAbnormalResponse(client_id, request.getParameter("grant_type"), OauthConstants.INVALID_REFRESH_TOKEN);
                            }
                        } catch (Exception e) {
                            logger.error("{}<{}>{}{}",
                                    new Object[]{request.getParameter("client_id"), request.getParameter("grant_type"), "checkRefreshToken查询刷新token异常:", e});
                            return ResponseUtil.getAbnormalResponse(request.getParameter("client_id"), request.getParameter("grant_type"), OauthConstants.SERVER_EXCEPTION);
                        }
                        //生成refresh Token
                        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                        refreshToken = oauthIssuerImpl.refreshToken();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("REFRESHTOKEN_OLD", refresh_token);
                        map.put("REFRESHTOKEN", refreshToken);
                        map.put("EXPIREIN", String.valueOf(refreshToken_expireIn));
                        String app_account_id;
                        try {
                            oAuthService.updateRefreshToken(map);
                            //获取app_account_id
                            app_account_id = oAuthService.findAppAccountIDByRefreshToken(refreshToken);
                        } catch (Exception e) {
                            logger.error("{}<{}>{}{}",
                                    new Object[]{request.getParameter("client_id"), request.getParameter("grant_type"), "updateRefreshToken或findAppAccountIDByRefreshToken，更新token或查询用户id异常:", e});
                            return ResponseUtil.getAbnormalResponse(request.getParameter("client_id"), request.getParameter("grant_type"), OauthConstants.SERVER_EXCEPTION);
                        }

                        Map<String, Object> params = new HashMap<String, Object>();
                        //获取过期时间
                        params.put("EXPIREIN", accessToken_expireIn);
                        params.put("SCOPE", scope);
                        params.put("APP_ACCOUNT_ID", app_account_id);
                        //生成access Token
                        accessToken = JwtTokenUtil.generateToken(params);
                    }
            //生成OAuth响应
            OAuthResponse response = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(String.valueOf(OauthConstants.ACCESS_TOKEN_EXPIREIN))
                    .setRefreshToken(refreshToken)
                    .buildJSONMessage();

            logger.info("{}<{}>获取令牌，应答信息:{}", client_id, request.getParameter("grant_type"), response.getBody());
            JSONObject json = JSONObject.parseObject(response.getBody());
            return json;
        } catch (OAuthProblemException e) {
            logger.error("{}<{}>{}{}",
                    new Object[]{request.getParameter("client_id"), request.getParameter("grant_type"), "OAuthProblemException异常:", e});
            //出错处理
            return ResponseUtil.getAbnormalResponse(request.getParameter("client_id"), request.getParameter("grant_type"), OauthConstants.INVALID_PARAMETER);
        }
    }

}
