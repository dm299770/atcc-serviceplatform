package com.acv.cloud.services.sms;

import com.acv.cloud.models.mongdb.sms.SMS;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by liyang on 2018/12/24.
 */
public interface SMSService {

    /**
     * 发送短信接口
     *
     * @return
     */
    JSONObject sendSms(SMS sms);
}
