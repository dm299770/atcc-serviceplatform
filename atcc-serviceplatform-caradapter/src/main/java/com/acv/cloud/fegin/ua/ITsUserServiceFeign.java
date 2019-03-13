package com.acv.cloud.fegin.ua;


import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.Notification;

@FeignClient(name = "ACV-UA")
public interface ITsUserServiceFeign {
    /**
     * 用账号推送通知给单个IOS设备
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/push")
    public Object pushMsg(@RequestBody Notification no);

    /**
     * 推送给单个app的所有IOS设备
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pushAll")
    public Object pushMsgAll(@RequestBody Notification no);

    /**
     * 用账号推送通知给多个IOS设备
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pushList")
    public JSONObject pushMsgList(@RequestBody Notification no);
}
