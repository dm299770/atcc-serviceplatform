package com.acv.cloud.feign.useradapter;

import com.acv.cloud.model.ua.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-useradapter")
public interface ITsUserServiceFeign {

    @RequestMapping(value = "user/{id}")
    @ResponseBody
    public UserInfo getUser(@PathVariable("id")String uuid);

}
