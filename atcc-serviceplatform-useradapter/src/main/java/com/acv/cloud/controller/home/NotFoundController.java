package com.acv.cloud.controller.home;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;

import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class NotFoundController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/erro";
    }

    @RequestMapping(value = "/error")
    public Object error(HttpServletResponse response, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppResultConstants.MSG, AppResultConstants.NOTFOUND_URL);
        jsonObject.put(AppResultConstants.STATUS, AppResultConstants.NOTFOUND_ERROR);
        return jsonObject;

    }

}
