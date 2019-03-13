package com.acv.cloud.frame.constants;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.models.jsonBean.smartSpeaker.mi.MiTaskRequest;

public class DataFormat {
    public static MiTaskRequest parseToQuery(JSONObject jsonParam){
        String intent_name = jsonParam.getJSONObject("request").getJSONObject("slot_info").getString("intent_name");
        String sessionId = jsonParam.getJSONObject("session").getString("session_id");

        MiTaskRequest miTaskRequest = new MiTaskRequest();
        miTaskRequest.setIntentName(intent_name);
        miTaskRequest.setSessionId(sessionId);

        return miTaskRequest;

    }
}
