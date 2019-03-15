package com.acv.cloud.filter;

import com.acv.cloud.constants.RedisConstants;
import com.acv.cloud.constants.useradapter.CurrentUserConstants;
import com.acv.cloud.feign.authentication.IAuthenticationFeign;
import com.acv.cloud.feign.useradapter.ITsUserServiceFeign;
import com.acv.cloud.model.ua.UserInfo;
//import com.acv.cloud.util.JwtTokenUtil;
import com.acv.cloud.util.JwtTokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
@Component
public class AccessFilter extends ZuulFilter
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String accessToken = "accessToken";

    @Autowired
    private ITsUserServiceFeign iTsUserServiceFeign;

    @Autowired
    private IAuthenticationFeign iAuthenticationFeign;


    /**
     * pre请求之前
     * route用于将请求路由转到微服务
     * post路由到微服务以后执行
     * error在其他阶段发生错误的时候执行
     * @return
     */

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序
     * @return
     */

    @Override
    public int filterOrder() {
        return 0;
    }
    /**
     * true返回一个boolean判断该过滤器是否要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String url = ctx.getRequest().getRequestURL().toString();
        //判断当前请求是否验证登录
        return needCertification(url);
    }
    /**
     * 过滤器执行具体内容
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException{
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>send {} request to {}",request.getMethod(),request.getRequestURL().toString());
        Object header = request.getHeader(accessToken);


        if(header!=null){
            logger.info("--->>token: {}",header.toString());
            if (!JwtTokenUtil.checkAccessToken(header.toString())) {
                //Claims claims = TokenUtils.parseJWT(accessToken);
                Map<String, Object> map = JwtTokenUtil.analysisToken(header.toString(), 1);

                String uuid = map.get("id").toString();//用户id
                String deviceType = map.get(RedisConstants.LOGIN_DEVICETYPE).toString();//登录认证设备型号
                //String deviceType = map.get("LOGIN_DEVICETYPE").toString();//登录认证设备型号
                String deviceNo = map.get(RedisConstants.LOGIN_DEVICENO).toString();//登陆认证设备推送号

                if(!containsType(deviceType)){
                    logger.info("CurrentUser is logining deviceType :{}",deviceType);
                    ctx.setResponseStatusCode(401);
                    ctx.setResponseBody("{\"status\":400,\"msg\":\"未知设备,请重新登录\"}");
                    ctx.getResponse().setContentType("text/html;charset=UTF-8");
                    return null;

                }
                //如果是手机设备
                if(containsType("IOS")||containsType("Android")){

                    if(deviceNo == null){
                        ctx.setResponseStatusCode(401);
                        ctx.setResponseBody("{\"status\":400,\"msg\":\"未知设备,请重新登录\"}");
                        ctx.getResponse().setContentType("text/html;charset=UTF-8");
                        return null;
                    }
                    JSONObject deviceObject = iAuthenticationFeign.getDeviceNo(uuid,deviceType);

                    if(deviceObject!=null){
                        //String deviceNoCache = deviceObject.get("deviceNo").toString();
                        String deviceNoCache = deviceObject.get("deviceNo").toString();
                        if(!deviceNo.equals(deviceNoCache)){
                            //throw new Exception("账号在其他设备登录,请重新登录");
                            ctx.setResponseStatusCode(401);
                            ctx.setResponseBody("{\"status\":400,\"msg\":\"账号在其他设备登录,请重新登录\"}");
                            ctx.getResponse().setContentType("text/html;charset=UTF-8");
                            return null;
                        }
                    }

                }

                UserInfo userInfo = null;
                if (!StringUtils.isEmpty(uuid)) {
                    userInfo = iTsUserServiceFeign.getUser(uuid);
                }
                if(userInfo!=null){
                    // 当前登录用户@CurrentUser
                    request.setAttribute(CurrentUserConstants.CURRENT_USER, userInfo);
                    logger.info("CurrentUser is logining userid :{}",uuid);
                    try {

                        ctx.addZuulRequestHeader("zuul_userInfo", URLEncoder.encode(JSON.toJSONString(userInfo),"UTF-8"));
                        ctx.addZuulRequestHeader("zuul_accessToken",URLEncoder.encode(JSON.toJSONString(header.toString()),"UTF-8"));//accessToken 存入request

                    } catch (UnsupportedEncodingException e) {
                        logger.warn("----------------->userInfo encoder exception!");
                        e.printStackTrace();
                    }
                    //ctx.addZuulRequestHeader("Content-type", "application/json; charset=utf-8");

                    //另外在request中虽然可以setAttribute（），但是可能由于作用域（request）的不同，一台服务器""才能getAttribute（）出来，
                    //在这里设置的Attribute在后续的微服务中是获取不到的，因此必须考虑另外的方式：get方法和其他方法处理方式不同，post和put
                    //需重写HttpServletRequestWrapper，即获取请求的输入流，重写json参数，传入重写构造上下文中的request中。

//                    try {
//                        InputStream in = ctx.getRequest().getInputStream();
//                        String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
//                        if(StringUtils.isBlank(body)){
//                            body = "{}";
//                        }
//                        JSONObject jsonObject = JSON.parseObject(body);
//                        jsonObject.put(CurrentUserConstants.CURRENT_USER, userInfo);
//                        String newBody = jsonObject.toString();
//                        final byte[] reqBodyBytes = newBody.getBytes();
//                        ctx.setRequest(new HttpServletRequestWrapper(request){
//                            @Override
//                            public ServletInputStream getInputStream() throws IOException {
//                                return new ServletInputStreamWrapper(reqBodyBytes);
//                            }
//                            @Override
//                            public int getContentLength() {
//                                return reqBodyBytes.length;
//                            }
//                            @Override
//                            public long getContentLengthLong() {
//                                return reqBodyBytes.length;
//                            }
//                        });
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }

            }
        }else{
            logger.warn("token is empty");
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"status\":400,\"msg\":\"accessToken为空,请登录\"}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            return null;
        }
        return null;
    }
    /**
     * @param url 请求url
     * */
    private Boolean needCertification(String url){
        Boolean falg = true;

        if(url.contains("/auth/login/")
                ||url.contains("/user/registeredUser")
                ||url.contains("/user/getCode")
                ||url.contains("/user/create")
        ){
            falg = false;
        }

        return falg;
    }

   private static boolean containsType(String type) {

        for (RedisConstants.ALL_DEVICETYPES devicetypes : RedisConstants.ALL_DEVICETYPES.values()) {
            if (devicetypes.name().equals(type)) {
                return true;
            }
        }

        return false;
    }

    private  enum deviceno {IOS, Android, Oauth2, WeChart}

    public static void main(String[] args){

        System.out.println(containsType("ios"));

    }
}
