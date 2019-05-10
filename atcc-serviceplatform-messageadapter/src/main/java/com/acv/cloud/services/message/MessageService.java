package com.acv.cloud.services.message;

import com.acv.cloud.domain.body.req.message.GetParams;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by liyang on 2019/01/10.
 */
public interface MessageService {

    JSONObject selectMessage(GetParams message);

    JSONObject delMessage(String ids);

}
