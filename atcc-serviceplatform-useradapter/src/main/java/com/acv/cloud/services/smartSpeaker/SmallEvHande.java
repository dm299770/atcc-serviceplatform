package com.acv.cloud.services.smartSpeaker;


import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.smartSpeaker.TaskRequest;


public interface SmallEvHande {

    //public TaskResult execute(TaskQuery taskQuery);

    /**运行处理智能音箱意图请求*/
     JSONObject execute(TaskRequest taskRequest);

}

