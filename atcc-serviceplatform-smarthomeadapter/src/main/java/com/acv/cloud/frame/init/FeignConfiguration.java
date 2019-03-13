package com.acv.cloud.frame.init;

import com.acv.cloud.frame.constants.JwtConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Configuration
public class FeignConfiguration implements RequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String accessToken = request.getHeader(JwtConstants.ZUUL_ACCESSTOKEN);
        String userInfoJson = request.getHeader(JwtConstants.ZUUL_USERINFO);

        logger.info("FeignInterceptor： get accessToken 【"+accessToken+"】");
        logger.info("FeignInterceptor: get userInfo 【"+userInfoJson+"】");

        requestTemplate.header(JwtConstants.ZUUL_ACCESSTOKEN,accessToken);
        requestTemplate.header(JwtConstants.ZUUL_USERINFO,userInfoJson);


    }
}
