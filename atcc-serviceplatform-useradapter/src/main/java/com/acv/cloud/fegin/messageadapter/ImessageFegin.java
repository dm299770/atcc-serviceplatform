package com.acv.cloud.fegin.messageadapter;

import com.acv.cloud.jsonBean.fegin.messageadapter.SMS;
import com.acv.cloud.jsonBean.fegin.messageadapter.sms.SMSParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-messageadapter")
public interface ImessageFegin {

    /**
     * 根据手机号发送短信
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/sms/v1",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object sendSmsToPhone(@RequestBody SMSParams sms) ;



}
