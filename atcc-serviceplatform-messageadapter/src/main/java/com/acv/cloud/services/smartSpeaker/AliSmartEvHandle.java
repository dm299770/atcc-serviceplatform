package com.acv.cloud.services.smartSpeaker;

import com.acv.cloud.models.jsonBean.smartSpeaker.TaskRequest;
import com.alibaba.fastjson.JSONObject;

public interface AliSmartEvHandle {
    JSONObject execute(TaskRequest taskRequest);
}
