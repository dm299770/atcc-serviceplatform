package com.acv.cloud.services.smartSpeaker.impl;


import com.acv.cloud.Enum.ExecuteCode;
import com.acv.cloud.models.jsonBean.smartSpeaker.IntentModel;
import com.acv.cloud.models.jsonBean.smartSpeaker.TaskRequest;
import com.acv.cloud.models.jsonBean.smartSpeaker.ali.AliTaskResponse;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.smartSpeaker.AliSmartEvHandle;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AliSmartEvHandleImpl implements AliSmartEvHandle {
    private static final Logger logger = LoggerFactory.getLogger(AliSmartEvHandleImpl.class);

    @Autowired
    RedisRepository redisRepository;

    @Override
    public JSONObject execute(TaskRequest taskRequest) {
        JSONObject jsonObject = new JSONObject();
        String intentName = taskRequest.getIntentName();
        String sessionId = taskRequest.getSessionId();
        String reply = "我不清楚你在说什么";
        String returnCode = "-1";//失败
        ExecuteCode executeCode = ExecuteCode.PARAMS_ERROR;

        redisRepository.init(1);

        AliTaskResponse aliTaskResponse = new AliTaskResponse(returnCode, reply, executeCode);
        if (intentName != null && !"".equals(intentName)) {//判断意图名
            switch (intentName) {
                case "RANGE":
                    aliTaskResponse.setReply(/*customizeConfigure.getRANGE()*/"");
                    break;
                case "LOC":
                    aliTaskResponse.setReply(/*customizeConfigure.getLOC()*/"");
                    break;
                case "SOC":
                    aliTaskResponse.setReply(/*customizeConfigure.getSOC()*/"");
                    break;
                case "PRECDT":
                    doPrecdt(sessionId, aliTaskResponse, intentName);
//                    IsConfirm(sessionId,  aliTaskResponse,  true,  "已帮您开启空调,预热大概持续5分钟");
                    break;
                case "CONFMD":
                    IsConfirm(sessionId, aliTaskResponse, true, "已帮您开启空调,预热大概持续5分钟");
                    break;//是
                case "NOTCFM":
                    IsConfirm(sessionId, aliTaskResponse, false, "好的,已帮您取消空调预热");
                    break;//否
                default:
                    defaultHandle(sessionId, aliTaskResponse);
            }
        }
        String json = "{" +
                "\"returnCode\":\"" + aliTaskResponse.getReturnCode() + "\"," +
                "\"returnErrorSolution\":\" \"," +
                "\"returnMessage\":\" \"," +
                "\"returnValue\": {" +
                "\"reply\":\"" + aliTaskResponse.getReply() + "\"," +
                "\"resultType\":\" \"," +
                "\"actions\":[" +
                "]," +
                "\"properties\": {" + "}," +
                "\"executeCode\":\"" + aliTaskResponse.getExecuteCode() + "\"," +
                "\"msgInfo\":\" \"" +
                "}" +
                "}";

        logger.info("response json :" + json);

        return JSONObject.parseObject(json);
    }

    /**
     * 预询问意图
     */
    private AliTaskResponse doPrecdt(String sessionId, AliTaskResponse aliTaskResponse, String intentName) {
        if (sessionId != null && !"".equals(sessionId)) {
            //根据sessionId查询意图
            if (redisRepository.exists(sessionId)) {
                //cache中存在当前会话,加入意图链表
                List<IntentModel> intentModels = (List<IntentModel>) redisRepository.get(sessionId);
                IntentModel intentModel = new IntentModel(intentName, System.currentTimeMillis());
                intentModels.add(intentModel);
                redisRepository.set(sessionId, intentModels);
            } else {
                //cache中不存在当前会话,保存到缓存
                List<IntentModel> intentModels = new ArrayList<IntentModel>();
                IntentModel intentModel = new IntentModel(intentName, System.currentTimeMillis());
                intentModels.add(intentModel);
                redisRepository.set(sessionId, intentModels);
            }
            aliTaskResponse.setReply("开启空调将会消耗大约5公里里程的电,是否确认?");

        } else {
            aliTaskResponse.setReply("当前会话出现异常,请重新询问您的问题");
        }
        return aliTaskResponse;
    }

    /**
     * 确认询问意图
     */
    private AliTaskResponse IsConfirm(String sessionId, AliTaskResponse aliTaskResponse, Boolean isConfirm, String reply) {
        if (sessionId != null && !"".equals(sessionId)) {
            //根据sessionId查询意图
            if (redisRepository.exists(sessionId)) {
                //cache中存在当前会话
                List<IntentModel> intentModels = (List<IntentModel>) redisRepository.get(sessionId);
                String isConfirmIntentName = null;
                if (intentModels.size() > 0) {
                    isConfirmIntentName = intentModels.get(intentModels.size() - 1).getIntentName();
                }
                //找到相应意图,如何处理?
                //根据isConfirm确定/否定
                if (isConfirm) {
                    //确定
                    aliTaskResponse.setReply(reply);
                    aliTaskResponse.setExecuteCode(ExecuteCode.SUCCESS);
                    aliTaskResponse.setReturnCode("0");
                } else {
                    //否定
                    aliTaskResponse.setReply(reply);
                    aliTaskResponse.setExecuteCode(ExecuteCode.REPLY_ERROR);
                    aliTaskResponse.setReturnCode("-1");
                }
            } else {
                //cache中不存在当前会话
                aliTaskResponse.setReply("我还不够聪明,不清楚您要确认什么.");
            }
        } else {
            aliTaskResponse.setReply("当前会话出现异常,请重新询问您的问题");
        }
        return aliTaskResponse;
    }

    /**
     * 默认意图
     */
    private AliTaskResponse defaultHandle(String sessionId, AliTaskResponse aliTaskResponse) {
        //根据session id 查询是否有上级意图
        if (sessionId != null && !"".equals(sessionId)) {
            //根据sessionId查询意图
            if (redisRepository.exists(sessionId)) {
                //缓存中存在意图链,取出最后意图
                List<IntentModel> intentModels = (List<IntentModel>) redisRepository.get(sessionId);
                if (intentModels != null && intentModels.size() > 0) {
                    if (intentModels.size() > 2) {//意图链过长
                        aliTaskResponse.setReply("重复询问次数过多,请重新发起会话");
                    } else {
                        //执行最后意图
                        String isConfirmIntentName = intentModels.get(intentModels.size() - 1).getIntentName();
                        aliTaskResponse = doPrecdt(sessionId, aliTaskResponse, isConfirmIntentName);
                    }
                } else {
                    aliTaskResponse.setReply("我还不够聪明,不能确定您询问什么");
                }

            } else {
                //cache中没有当前会话意图链,如何处理?
                aliTaskResponse.setReply("我还不够聪明,不能确定您询问什么");
            }
        } else {
            aliTaskResponse.setReply("当前会话出现异常,请重新描述您的指令");
        }
        return aliTaskResponse;
    }
}
