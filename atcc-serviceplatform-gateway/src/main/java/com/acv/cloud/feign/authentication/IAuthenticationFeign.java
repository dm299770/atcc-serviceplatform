package com.acv.cloud.feign.authentication;

import com.alibaba.fastjson.JSONObject;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-authentication")
public interface IAuthenticationFeign {

    @RequestMapping("/device/getDeviceNoByCache/{userid}/{deviceType}")
    @ResponseBody
    public JSONObject getDeviceNo(@PathVariable("userid")String userid , @PathVariable("deviceType") String deviceType);
}
