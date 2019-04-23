package com.acv.cloud.controller.login;

import com.acv.cloud.domain.body.req.login.Attributes;
import com.acv.cloud.domain.body.req.login.Data;
import com.acv.cloud.domain.body.req.login.LoginParams;
import com.acv.cloud.frame.constants.RedisConstants;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.services.login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @description:登录
 * @author:@leo.
 */
@RestController
@RequestMapping({"/auth"})
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "login/{version}")
    public Object login(@RequestBody LoginParams params,@PathVariable String version) {

            logger.info("LoginController: login params :{} --version:{}" , params.toString(), version);

            //暂未开发version检测 后续开发

            String phoneNum = Optional.ofNullable(params)
                    .map(LoginParams::getData)
                    .map(Data::getAttributes)
                    .map(Attributes::getPhoneNum).orElse(null);

            String password = Optional.ofNullable(params)
                    .map(LoginParams::getData)
                    .map(Data::getAttributes)
                    .map(Attributes::getPassword).orElse(null);

            String deviceNo = Optional.ofNullable(params)
                    .map(LoginParams::getData)
                    .map(Data::getAttributes)
                    .map(Attributes::getDeviceNo).orElse(null);

            String deviceType = Optional.ofNullable(params)
                    .map(LoginParams::getData)
                    .map(Data::getAttributes)
                    .map(Attributes::getDeviceType).orElse(null);




        JSONObject result = loginService.login(phoneNum, password, deviceType, deviceNo);

        return result;

    }



}
