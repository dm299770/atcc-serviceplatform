package com.acv.cloud.controller.oauth;

import com.acv.cloud.fegin.authentication.LoginFegin;
import com.acv.cloud.fegin.useradapter.ITsUserServiceFeign;
import com.acv.cloud.frame.util.StrUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.OauthConstants;
import com.acv.cloud.frame.util.MD5Util;
import com.acv.cloud.frame.util.ResponseUtil;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.repository.redistemplate.IUserDao;
import com.acv.cloud.services.login.LoginService;
import com.acv.cloud.services.oauth.OAuthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

//import org.apache.commons.lang.StringUtils;

@Controller
public class AuthorizeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private LoginFegin loginFegin;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/oauth2/authorize")
    public Object authorize(HttpServletRequest request, ModelMap map) throws URISyntaxException, OAuthSystemException {
        try {
            if (request.getParameter("flag") == null) {
                String st = String.valueOf(System.currentTimeMillis() / 1000);
                //request.setAttribute("st", st);
                map.put("st", st);
                logger.info("时间戳：{},进行授权请求：client_id:{}，response_type:{}，scope:{}，state:{}，redirect_uri:{}", st, request.getParameter("client_id"), request.getParameter("response_type"),
                        request.getParameter("response_type"), request.getParameter("scope"), request.getParameter("state"), request.getParameter("redirect_uri"));
            }

            if (request.getParameter("client_id") != null) {
                map.put("client_id", request.getParameter("client_id"));
            } else {
                map.put("client_id", "");
            }

            if (request.getParameter("response_type") != null) {
                map.put("response_type", request.getParameter("response_type"));
            } else {
                map.put("response_type", "");
            }

            if (request.getParameter("scope") != null) {
                map.put("scope", request.getParameter("scope"));
            } else {
                map.put("scope", "");
            }

            if (request.getParameter("state") != null) {
                map.put("state", request.getParameter("state"));
            } else {
                map.put("state", "");
            }

            if (request.getParameter("redirect_uri" +
                    "") != null) {
                map.put("redirect_uri", request.getParameter("redirect_uri"));
            } else {
                map.put("redirect_uri", "");
            }
            //构建OAuth授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            //校验client_id
            try {
                Map<String, Object> checkOAuthThridparty = oAuthService.checkOAuthThridparty(oauthRequest.getClientId(), null);
                //检查传入的客户端id是否正确
                if (checkOAuthThridparty == null) {
                    return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("response_type"), OauthConstants.INVALID_CLIENT_ID);
                }
            } catch (Exception e) {
                logger.error("{}<{}>{}{}",
                        new Object[]{oauthRequest.getClientId(), request.getParameter("response_type"), "checkOAuthThridparty查询传入的客户端id异常:", e});
                return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("response_type"), OauthConstants.SERVER_EXCEPTION);
            }

            //校验redirect_uri
            if ("".equals(oauthRequest.getRedirectURI()) || oauthRequest.getRedirectURI() == null) {
                return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("response_type"), OauthConstants.INVALID_PARAMETER);
            }
            //校验scope
            if("".equals(oauthRequest.getParam(OAuth.OAUTH_SCOPE))
                    || (oauthRequest.getParam(OAuth.OAUTH_SCOPE) == null)
                    || !StrUtils.checkScope(oauthRequest.getParam(OAuth.OAUTH_SCOPE))){
               	return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("response_type"), OauthConstants.INVALID_SCOPE);
            }
            //校验state
            if ("".equals(oauthRequest.getState()) || oauthRequest.getState() == null) {
                return ResponseUtil.getAbnormalResponse(oauthRequest.getClientId(), request.getParameter("response_type"), OauthConstants.INVALID_PARAMETER);
            }

            //如果用户没有登录，跳转到登陆页面
            if (!login(request)) {//登录失败时跳转到登陆页面
                if (request.getParameter("flag") == null) {
                    logger.info("时间戳：{}---<{}>已授权，需登录获取授权码", String.valueOf(request.getAttribute("st")), request.getParameter("client_id"));
                }
                return "login";
            }
            String app_account_id = String.valueOf(request.getAttribute("app_account_id"));
            //生成授权码
            String authorizationCode = null;
            //responseType目前支持CODE和TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                //RedisUtils.setex(RedisConnection.getShardedJedis(), authorizationCode, app_account_id);
                userDao.addAuthCode(String.format(OauthConstants.PROTOCOL+":%s:%s",oauthRequest.getClientId(),authorizationCode),app_account_id);

            }
            //进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            //设置授权码
            builder.setCode(authorizationCode);
            //builder.setParam("state", request.getParameter("state"));
            //builder.setParam("client_id", "oauth_clientid_aligenie");
            //builder.setParam("client_secret", "oauth_clientid_aligenie");
            //builder.setParam("response_type", "code");
            //builder.setParam("grant_type", "authorization_code");

            //得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            //构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            @SuppressWarnings("rawtypes")
            HttpEntity httpEntity = new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
            logger.info("时间戳：{}---<{}>获取授权码：{}，授权应答参数httpEntity：{}", request.getParameter("st"), request.getParameter("client_id"), authorizationCode, httpEntity);
            return httpEntity;
        } catch (OAuthProblemException e) {
            logger.error("{}<{}>{}{}",
                    new Object[]{request.getParameter("client_id"), request.getParameter("response_type"), "OAuthProblemException异常:", e});
            //出错处理
            return ResponseUtil.getAbnormalResponse(request.getParameter("client_id"), request.getParameter("response_type"), OauthConstants.INVALID_PARAMETER);
        }

    }

    private boolean login(HttpServletRequest request) {


        String phoneNum = request.getParameter("phoneNum");
        String password = request.getParameter("password");
        String flag = request.getParameter("flag");

        if (StringUtils.isEmpty(phoneNum) || StringUtils.isEmpty(password)) {
            if (flag != null && "1".equals(flag)) {
                request.setAttribute("error", "用户名密码为空");
            }
            return false;
        }
        try {
            //查询用户信息
            //TsUser user = oAuthService.findByPhoneNum(phoneNum);
            //JSONObject jsonObject = loginService.login(phoneNum, MD5Util.md5(password), "");
            Object object = loginFegin.login(phoneNum,MD5Util.md5(password),"webclient");
            JSONObject jsonObject = JSONObject.parseObject(object.toString());

            if (jsonObject.getInteger(AppResultConstants.STATUS) != 200) {
                request.setAttribute("error", "账号或密码不正确");
                return false;
            }
            request.setAttribute("app_account_id",phoneNum);
        } catch (Exception e) {
            logger.error("{}<{}>{}{}",
                    new Object[]{request.getParameter("client_id"), request.getParameter("response_type"), "findByUsername查询用户信息异常:", e});
            return false;
        }

        return true;
    }
}