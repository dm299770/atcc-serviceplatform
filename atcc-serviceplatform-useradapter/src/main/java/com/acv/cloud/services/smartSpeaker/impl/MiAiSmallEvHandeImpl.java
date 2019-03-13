package com.acv.cloud.services.smartSpeaker.impl;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.models.jsonBean.smartSpeaker.IntentModel;
import com.acv.cloud.models.jsonBean.smartSpeaker.TaskRequest;
import com.acv.cloud.models.jsonBean.smartSpeaker.mi.MiTaskResponse;
import com.acv.cloud.models.jsonBean.vehicle.status.ALLVehicleState;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.smartSpeaker.SmallEvHande;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MiAiSmallEvHandeImpl implements SmallEvHande {

    private static final Logger logger = LoggerFactory.getLogger(MiAiSmallEvHandeImpl.class);
    //@Autowired
    //CustomizeConfigure customizeConfigure;
    @Autowired
    RedisRepository redisRepository;

    @Override
    public JSONObject execute(TaskRequest taskRequest) {
        JSONObject jsonObject = new JSONObject();
        String intentName = taskRequest.getIntentName();
        String sessionId =taskRequest.getSessionId();
        String speak = "我不清楚你在说什么";
        Boolean open_mic = false;
        Boolean is_session_end = true;

        MiTaskResponse miTaskResponse = new MiTaskResponse(open_mic,is_session_end,speak);
        //miTaskResponse.setOpen_mic(open_mic);
        //miTaskResponse.setIs_session_end(is_session_end);
//        try {
//           // customizeConfigure.init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        redisRepository.init(1);
        ALLVehicleState allVehicleState = (ALLVehicleState)redisRepository.get("NISSAN0000000000");
        if(allVehicleState!=null){

        }


        if(intentName!=null &&!"".equals(intentName)){
            switch(intentName){
                case "RANGE" :   miTaskResponse.setSpeaker(/*customizeConfigure.getRANGE()*/"");break;
                case "LOC" : miTaskResponse.setSpeaker(/*customizeConfigure.getLOC()*/"");break;
                case "SOC" : miTaskResponse.setSpeaker(/*customizeConfigure.getSOC()*/"");break;
                case "PRECDT" : doPrecdt(sessionId,miTaskResponse,intentName); break;
                case "CONFMD" : IsConfirm(sessionId,miTaskResponse,true,"已帮您开启空调,预热大概持续5分钟");break;
                case "NOTCFM" : IsConfirm(sessionId,miTaskResponse,false,"好的,已帮您取消空调预热");break;
                default: defaultHandle(sessionId,miTaskResponse);
            }

        }
        String json = "{" +
                "\"version\": \"1.0\"," +
                "\"response\": {" +
                "\"to_speak\": {" +
                "\"type\": 0," +
                "\"text\":\""+miTaskResponse.getSpeaker()+"\"" +
                "}," +
                "\"open_mic\":"+miTaskResponse.isOpen_mic() +
                "}," +
                "\"is_session_end\":"+miTaskResponse.isIs_session_end() +
                "}";
        logger.info("response json :" +json);

        return JSONObject.parseObject(json);
    }

    /**预询问意图*/
    private MiTaskResponse doPrecdt(String sessionId,MiTaskResponse miTaskResponse,String intentName){
        if(sessionId!=null && !"".equals(sessionId)){
            //根据sessionId查询意图
            if(redisRepository.exists(sessionId)){
                //cache中存在当前会话,加入意图链表
                List<IntentModel> intentModels = (List<IntentModel>)redisRepository.get(sessionId);
                IntentModel intentModel = new IntentModel(intentName,System.currentTimeMillis());
                intentModels.add(intentModel);
                redisRepository.set(sessionId,intentModels);
            }else{
                //cache中不存在当前会话,保存到缓存
                List<IntentModel> intentModels = new ArrayList<IntentModel>();
                IntentModel intentModel = new IntentModel(intentName,System.currentTimeMillis());
                intentModels.add(intentModel);
                redisRepository.set(sessionId,intentModels);
            }
            miTaskResponse.setSpeaker(/*customizeConfigure.getPRECDT()*/"开启空调将会消耗大约5公里里程的电,是否确认?");
            miTaskResponse.setOpen_mic(true);
            miTaskResponse.setIs_session_end(false);
        }else{
            miTaskResponse.setSpeaker("当前会话出现异常,请重新询问您的问题");
            miTaskResponse.setOpen_mic(true);
            miTaskResponse.setIs_session_end(false);
        }
        return miTaskResponse;
    }
    /**确认询问意图*/
    private MiTaskResponse IsConfirm(String sessionId,MiTaskResponse miTaskResponse,Boolean isConfirm, String speaker){
        if(sessionId!=null && !"".equals(sessionId)){
            //根据sessionId查询意图
            if(redisRepository.exists(sessionId)){
                //cache中存在当前会话
                List<IntentModel> intentModels = (List<IntentModel>)redisRepository.get(sessionId);
                String isConfirmIntentName= null ;
                if(intentModels.size()>0){
                    isConfirmIntentName = intentModels.get(intentModels.size()-1).getIntentName();
                }

                //找到相应意图,如何处理?
                //根据isConfirm确定/否定
                if(isConfirm){
                    //确定
                    miTaskResponse.setSpeaker(speaker);
                }else{
                    //否定
                    miTaskResponse.setSpeaker(speaker);
                }

                //miTaskResponse.setOpen_mic(false);
                //miTaskResponse.setIs_session_end(true);

            }else{
                //cache中不存在当前会话
                miTaskResponse.setSpeaker("我还不够聪明,不清楚您要确认什么.");
                //miTaskResponse.setOpen_mic(false);
               // miTaskResponse.setIs_session_end(true);

            }
        }else{
            miTaskResponse.setSpeaker("当前会话出现异常,请重新询问您的问题");

        }
        miTaskResponse.setOpen_mic(false);
        miTaskResponse.setIs_session_end(true);
        return miTaskResponse;
    }
    /**默认意图*/
    private MiTaskResponse defaultHandle (String sessionId,MiTaskResponse miTaskResponse){
        //根据session id 查询是否有上级意图
        if(sessionId!=null && !"".equals(sessionId)){
            //根据sessionId查询意图
            if(redisRepository.exists(sessionId)){
                //缓存中存在意图链,取出最后意图
                List<IntentModel> intentModels = (List<IntentModel>)redisRepository.get(sessionId);
                if(intentModels!=null&&intentModels.size()>0){
                   if(intentModels.size()>2){//意图链过长
                       miTaskResponse.setSpeaker("重复询问次数过多,请重新发起会话");
                   }else{
                       //执行最后意图
                       String isConfirmIntentName = intentModels.get(intentModels.size()-1).getIntentName();
                       miTaskResponse = doPrecdt(sessionId,miTaskResponse,isConfirmIntentName);
                   }
                }else{
                    miTaskResponse.setSpeaker("我还不够聪明,不能确定您询问什么");
                    miTaskResponse.setOpen_mic(false);
                    miTaskResponse.setIs_session_end(true);
                }
               // miTaskResponse.setOpen_mic(false);
               // miTaskResponse.setIs_session_end(true);
            }else{
                //cache中没有当前会话意图链,如何处理?
                miTaskResponse.setSpeaker("我还不够聪明,不能确定您询问什么");
                miTaskResponse.setOpen_mic(false);
                miTaskResponse.setIs_session_end(true);
            }

        }else{
            miTaskResponse.setSpeaker("当前会话出现异常,请重新描述您的指令");
            miTaskResponse.setOpen_mic(false);
            miTaskResponse.setIs_session_end(true);
        }
        return miTaskResponse;
    }



}
