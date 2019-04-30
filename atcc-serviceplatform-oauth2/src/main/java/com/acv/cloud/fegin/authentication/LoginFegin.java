package com.acv.cloud.fegin.authentication;


import com.acv.cloud.domain.body.req.login.LoginParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-authentication")
public interface LoginFegin {
    @ResponseBody
    @RequestMapping(value = "/auth/login/v1-1",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object login(@RequestBody LoginParams params) ;
}
