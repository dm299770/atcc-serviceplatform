package com.acv.cloud.services.message;


import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.message.MessageRequestParameter;


/**
 * @description:消息中心查询消息
 * @author:@.
 */
public interface MessageService {
    /**
     * 远程车控
     *
     * @param  操作类型
     * @return 成功失败
     */
    JSONObject message(MessageRequestParameter request);
}

