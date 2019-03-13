package com.acv.cloud.controller.message;

import com.acv.cloud.models.jsonBean.message.request.MessageRequest;
import com.acv.cloud.models.mongdb.notification.Notification;
import com.acv.cloud.models.mongdb.sms.SMS;
import com.acv.cloud.services.message.MessageService;
import com.acv.cloud.services.notification.NotificationService;
import com.acv.cloud.services.sms.SMSService;
import com.acv.cloud.services.unread.UnReadService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公用通知查询接口
 * Created by liyang on 2019/01/10.
 */
@RestController
@RequestMapping({"/message"})
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private UnReadService unReadService;


    /**
     * 公用通知查询接口
     */
    @ResponseBody
    @RequestMapping(value = "/select")
    public Object selectMessage(@RequestBody MessageRequest message) {
        JSONObject result = messageService.selectMessage(message);
        return result;
    }


    /**
     * 根据手机号发送短信
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sms")
    public Object sendSmsToPhone(@RequestBody SMS sms) {
        JSONObject jsonObject = smsService.sendSms(sms);
        return jsonObject;
    }

    /**
     * 修改消息状态为已读
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public Object readSubmit(@RequestBody Notification no) {
        JSONObject json = unReadService.updateUnRead(no);
        return json;
    }


    /**
     * 推送给单个app的所有IOS设备
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pushAll")
    public Object pushMsgAll(@RequestBody Notification no) {
        JSONObject jsonObject = notificationService.pushMsgDeviceAll(no);
        return jsonObject;
    }

    /**
     * 用账号推送通知给多个IOS设备
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/push")
    public JSONObject pushMsgList(@RequestBody Notification no) {
        JSONObject jsonObject = notificationService.pushMsgDeviceList(no);
        return jsonObject;
    }
}
