package com.acv.cloud.fegin.ua.message;


import com.acv.cloud.models.fegin.Notification;
import com.acv.cloud.models.fegin.NotificationParams;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-messageadapter")
public interface MessageFegin {

    //车辆控制服务
    @ResponseBody
    @RequestMapping(value = "/message/push/v1",consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject pushMsgList(@RequestBody NotificationParams data);

}
