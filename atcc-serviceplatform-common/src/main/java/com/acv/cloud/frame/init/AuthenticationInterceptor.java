package com.acv.cloud.frame.init;


import com.acv.cloud.domain.dto.UserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.exception.LoginRequiredException;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.frame.constants.CurrentUserConstants;
import com.acv.cloud.frame.util.JwtTokenUtil;
import com.acv.cloud.frame.util.TokenUtils;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @description:Token验证过滤器,判断是否已登录
 * @author:@leo.
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public final static String ACCESS_TOKEN = "accessToken";

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws LoginRequiredException {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            //String accessToken = request.getParameter(ACCESS_TOKEN);
            //UserInfo user =  (UserInfo)request.getAttribute(CurrentUserConstants.CURRENT_USER);
            UserInfo user = null ;

//            try {
//                InputStream in = request.getInputStream();
//                String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
//                if(StringUtils.isNotBlank(body)){
//                    JSONObject jsonObject = JSON.parseObject(body);
//                    user =  JSON.parseObject(jsonObject.getJSONObject(CurrentUserConstants.CURRENT_USER).toJSONString(),UserInfo.class);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            //zuul HEADER 传递中文处理乱码问题
            String userJsonStr = request.getHeader("zuul_userInfo");
            if(StringUtils.isNotBlank(userJsonStr)){
                //user = JSON.parseObject(userJsonStr,UserInfo.class);

                try {
                    user = JSON.parseObject(URLDecoder.decode(userJsonStr, "UTF-8"),UserInfo.class);
                    //解码
                    }
                    catch (UnsupportedEncodingException var4) {
                    var4.printStackTrace();
                }


            }

            if (user == null) {
                //throw new RuntimeException("用户不存在，请重新登录");
                throw new LoginRequiredException("用户不存在，请重新登录");
            }
            // 当前登录用户@CurrentUser
            request.setAttribute(CurrentUserConstants.CURRENT_USER, user);


            return true;
        } else {
            return true;
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

}
