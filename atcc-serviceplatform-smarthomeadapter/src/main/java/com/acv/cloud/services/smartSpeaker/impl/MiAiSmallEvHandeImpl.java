package com.acv.cloud.services.smartSpeaker.impl;

import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.feign.caradapter.CarFegin;
import com.acv.cloud.feign.useradapter.UserFegin;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.util.FloatToPercentformat;
import com.acv.cloud.models.carControl.Vehicle;
import com.acv.cloud.models.carControl.VinRequestParameter;
import com.acv.cloud.models.vehicle.TrUserVin;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
public class MiAiSmallEvHandeImpl implements SmallEvHande {

    private static final Logger logger = LoggerFactory.getLogger(MiAiSmallEvHandeImpl.class);
    //@Autowired
    //CustomizeConfigure customizeConfigure;
    @Autowired
    RedisRepository redisRepository;
    @Autowired
    UserFegin userFegin;
    @Autowired
    CarFegin carFegin;

    @Override
    public JSONObject execute(UserInfo user , TaskRequest taskRequest) {
        JSONObject jsonObject = new JSONObject();
        String intentName = taskRequest.getIntentName();
        String sessionId =taskRequest.getSessionId();
        String speak = "我不清楚你在说什么";
        Boolean open_mic = false;
        Boolean is_session_end = true;

        //redisRepository.init(1);

        MiTaskResponse miTaskResponse = new MiTaskResponse(open_mic,is_session_end,speak);
        TrUserVin defaultVehicle = getDefaultVin(user);

        if(defaultVehicle==null){
            //未找到默认车辆
            speak = "未找到您的默认车辆,请绑定您的车辆";
        }else{
            //获取默认车辆的车辆状态
            ALLVehicleState vehicleState = getVehicleState(defaultVehicle);
            if(vehicleState!=null){

                miTaskResponse = doIntent(sessionId,intentName,miTaskResponse,vehicleState);

            }else{
                //获取车辆信息失败
                logger.info("远程调用车辆信息失败");
                miTaskResponse.setSpeaker("未找到"+defaultVehicle.getVin()+"车辆状态");
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

    /**查找用户默认车辆*/

    private TrUserVin getDefaultVin(UserInfo user){

        Object o = userFegin.getVehicleList(user);//获取用户绑定车辆列表

        if (o == null){
            //远程调用用户车辆服务失败
            logger.debug("MiAiSmallEvHandeImpl:远程调用用户车辆服务失败");

        }
        JSONObject vehilcesJson = JSONObject.parseObject(o.toString());

        List vehicles = null ;//用户名下所有绑定车辆
        TrUserVin defaultVehicle = null;//默认车辆

        if(vehilcesJson!=null){
            //获取当前用户所有车辆
            vehicles = JSONArray.toJavaObject(vehilcesJson.getJSONArray(AppResultConstants.DATA),List.class);

        }
        if(vehicles!=null && vehicles.size()>0){

            logger.info("MiAiSmallEvHandeImpl.execute: 当前用户所有车辆信息:"+vehicles.toString());
            Iterator it = vehicles.iterator();
            //获取默认车辆
            while(it.hasNext()){
                TrUserVin vin = JSON.toJavaObject(JSONObject.parseObject(it.next().toString()),TrUserVin.class);
                if(vin.getDefaultVehicle() ==1){
                    defaultVehicle = vin;
                    break;
                }

            }

        }

        return defaultVehicle;
    }

    //获取车辆状态
    private ALLVehicleState getVehicleState(TrUserVin vin){

        ALLVehicleState vehicleState = null;
        //远程获取车辆信息
        if(vin!=null){
            logger.info("MiAiSmallEvHandeImpl: 远程获取["+vin.toString()+"]状态");

            VinRequestParameter vinRequestParameter = new VinRequestParameter(new Vehicle(vin.getVin(),0));
            Object o =  carFegin.getVehicleState(vinRequestParameter);


            if (o == null){
                //远程调用车辆远程控制服务失败
                logger.debug("MiAiSmallEvHandeImpl:远程调用车辆远程控制服务失败");

            }

            JSONObject vehilceStatesJson = JSONObject.parseObject(o.toString());

            vehicleState = JSONObject.toJavaObject(vehilceStatesJson.getJSONObject(AppResultConstants.DATA),ALLVehicleState.class);



        }else{
            logger.info("获取状态信息车辆不存在");
        }

        return vehicleState;

    }

    /**意图处理反馈*/

    private MiTaskResponse doIntent(String sessionId,String intentName,MiTaskResponse miTaskResponse,ALLVehicleState vehicleState){

        //根据意图返回相应车辆状态
        if(intentName!=null &&!"".equals(intentName)){
            switch(intentName){
                case "RANGE" :   miTaskResponse.setSpeaker("还能行驶"+vehicleState.getUsablekm().intValue()+"公里呦");break;
                case "LOC" : miTaskResponse.setSpeaker("它在"+""+"附近");break;
                case "SOC" : miTaskResponse.setSpeaker("还有"+FloatToPercentformat.getPercentFormat(vehicleState.getResbatterycap(),2,0)+"的电");break;
                case "PRECDT" : doPrecdt(sessionId,miTaskResponse,intentName); break;
                case "CONFMD" : IsConfirm(sessionId,miTaskResponse,true,"已帮您开启空调,预热大概持续5分钟");break;
                case "NOTCFM" : IsConfirm(sessionId,miTaskResponse,false,"好的,已帮您取消空调预热");break;
                default: defaultHandle(sessionId,miTaskResponse);
            }

        }

        return miTaskResponse;
    }


}
