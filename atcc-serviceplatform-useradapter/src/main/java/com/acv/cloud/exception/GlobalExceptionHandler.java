package com.acv.cloud.exception;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        exception.printStackTrace();
        JSONObject jsonObjedct = new JSONObject();
        jsonObjedct.put(AppResultConstants.MSG, exception.getMessage());
        jsonObjedct.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        return jsonObjedct;
    }

//    @ExceptionHandler
//    public Object handleResourceNotFoundException(NoHandlerFoundException nhre) {
//        logger.error(nhre.getMessage(), nhre);
//        JSONObject jsonObjedct = new JSONObject();
//        jsonObjedct.put(AppResultConstants.MSG,nhre.getMessage() );
//        jsonObjedct.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
//        return jsonObjedct;
//    }


}
