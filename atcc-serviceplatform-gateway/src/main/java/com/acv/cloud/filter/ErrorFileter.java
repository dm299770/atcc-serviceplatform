package com.acv.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
@Component
public class ErrorFileter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(ErrorFileter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run()  {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        logger.error("this is a ErrorFilter: {}",throwable.getCause().getMessage());
        //ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        //ctx.set("error.exception",throwable.getCause());

        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("{\"status\":500,\"msg\":\"网关内部异常\"}");
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
