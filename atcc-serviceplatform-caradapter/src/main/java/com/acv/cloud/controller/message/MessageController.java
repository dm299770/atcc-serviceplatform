package com.acv.cloud.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.message.MessageRequestParameter;
import com.acv.cloud.services.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:消息中心查询消息
 * @author:@guo.zj
 */
@RestController
@RequestMapping({"/message"})
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageService messageService;
    @ResponseBody
    @RequestMapping(value = "selectmessage")
    public Object finder(@RequestBody MessageRequestParameter data) {
        JSONObject result=null;
        try {
            result = messageService.message(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}


