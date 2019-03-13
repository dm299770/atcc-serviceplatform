package com.acv.cloud.feign.useradapter;

import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-useradapter")
public interface UserFegin {

    /**
     * 获取当前登录用户下所有绑定车辆
     */


    @ResponseBody
    @RequestMapping(value = "/vehicle/getVehicleList",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getVehicleList(UserInfo user);

}
