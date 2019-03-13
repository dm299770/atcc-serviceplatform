package com.acv.cloud.fegin.authentication;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-authentication")
public interface LoginFegin {
    @ResponseBody
    @RequestMapping(value = "/login/login/{phoneNum}/{password}/{deviceNo}")
    public Object login(@PathVariable("phoneNum") String phoneNum, @PathVariable("password") String password, @PathVariable("deviceNo") String deviceNo);
}
