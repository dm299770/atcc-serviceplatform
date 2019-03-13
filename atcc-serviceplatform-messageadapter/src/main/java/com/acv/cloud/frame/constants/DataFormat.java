package com.acv.cloud.frame.constants;

import com.acv.cloud.models.jsonBean.smartSpeaker.ali.AliTaskRequest;
import com.alibaba.fastjson.JSONObject;

public class DataFormat {
    public static AliTaskRequest parseToQuery(JSONObject jsonParam) {
        System.out.println(jsonParam);
        String intentName = jsonParam.getString("intentName");
        String sessionId = jsonParam.getString("sessionId");
//        Long botId = jsonParam.getLong("botId");
//        Long skillId = jsonParam.getLong("skillId");
//        String skillName = jsonParam.getString("skillName");
//        String utterance = jsonParam.getString("utterance");
//
//        JSONArray slotEntities = jsonParam.getJSONArray("slotEntities");
//        List<TaskRequest> list = slotEntities.toJavaList(TaskRequest.class);
//
//        Long intentParameterId = list.get(0).getIntentParameterId();
//        String intentParameterName =list.get(0).getIntentParameterName();
//        String standardValue =list.get(0).getStandardValue();
//        String originalValue =list.get(0).getOriginalValue();
//        Integer liveTime =list.get(0).getLiveTime();
//        Long createTimeStamp =list.get(0).getCreateTimeStamp();

        AliTaskRequest aliTaskRequest = new AliTaskRequest();
        aliTaskRequest.setIntentName(intentName);
        aliTaskRequest.setSessionId(sessionId);
//        aliTaskRequest.setIntentParameterId(intentParameterId);
//        aliTaskRequest.setBotId(botId);
//        aliTaskRequest.setSkillId(skillId);
//        aliTaskRequest.setSkillName(skillName);
//        aliTaskRequest.setUtterance(utterance);
//        aliTaskRequest.setIntentParameterName(intentParameterName);
//        aliTaskRequest.setStandardValue(standardValue);
//        aliTaskRequest.setOriginalValue(originalValue);
//        aliTaskRequest.setLiveTime(liveTime);
//        aliTaskRequest.setCreateTimeStamp(createTimeStamp);
        return aliTaskRequest;
    }
}
