package com.acv.cloud.services.message;

import com.acv.cloud.models.jsonBean.message.request.MessageRequest;
import com.acv.cloud.models.mongdb.notification.Notification;
import com.acv.cloud.models.mongdb.sms.SMS;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by liyang on 2019/01/10.
 */
public interface MessageService {

    JSONObject selectMessage(MessageRequest message);


}
