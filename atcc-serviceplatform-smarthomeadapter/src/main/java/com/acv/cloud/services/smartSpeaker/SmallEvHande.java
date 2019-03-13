package com.acv.cloud.services.smartSpeaker;


import com.acv.cloud.dto.sys.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.models.jsonBean.smartSpeaker.TaskRequest;


public interface SmallEvHande {

    //public TaskResult execute(TaskQuery taskQuery);

    /**运行处理智能音箱意图请求*/
     JSONObject execute(UserInfo userInfo , TaskRequest taskRequest);

}

