package com.acv.cloud.filter;

import com.acv.cloud.constants.ua.CurrentUserConstants;
import com.acv.cloud.feign.ua.ITsUserServiceFeign;
import com.acv.cloud.model.ua.UserInfo;
//import com.acv.cloud.util.JwtTokenUtil;
import com.acv.cloud.util.JwtTokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
@Component
public class AccessFilter extends ZuulFilter
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String accessToken = "accessToken";

    @Autowired
    private ITsUserServiceFeign iTsUserServiceFeign;


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
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>send {} request to {}",request.getMethod(),request.getRequestURL().toString());
        Object header = request.getHeader(accessToken);


        if(header!=null){
            logger.info("--->>token: {}",header.toString());
            if (!JwtTokenUtil.checkAccessToken(header.toString())) {
                //Claims claims = TokenUtils.parseJWT(accessToken);
                Map<String, Object> map = JwtTokenUtil.analysisToken(header.toString(), 1);
                String uuid = map.get("id").toString();
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
        }
        return null;
    }
    /**
     * @param url 请求url
     * */
    private Boolean needCertification(String url){
        Boolean falg = true;

        if(url.contains("/login/login/")||url.contains("/user/registeredUser")){
            falg = false;
        }

        return falg;
    }
}
