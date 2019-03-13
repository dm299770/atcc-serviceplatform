package com.acv.cloud.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.services.login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:登录
 * @author:@leo.
 */
@RestController
@RequestMapping({"/login"})
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "login/{phoneNum}/{password}/{deviceNo}")
    public Object login(@PathVariable("phoneNum") String phoneNum, @PathVariable("password") String password, @PathVariable("deviceNo") String deviceNo) {
        JSONObject result = loginService.login(phoneNum, password, deviceNo);
        return result;
    }


}
