package com.acv.cloud.services.car.impl;


import com.acv.cloud.domain.body.req.car.control.ControlRequestmeters;
import com.acv.cloud.domain.dto.car.AirConditionCtrl;
import com.acv.cloud.domain.dto.car.EVVehicleCtrl;
import com.acv.cloud.domain.model.car.control.AirCondition;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.jsonBean.remote.parameter.AirConditionRequestParameter;
import com.acv.cloud.jsonBean.remote.parameter.EVvehicleCtrlRequestParameter;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.car.CarService;
import com.acv.cloud.services.remote.RemoteService;
import com.acv.cloud.services.vehicleState.VehicleStateService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:tsp 车辆远程控制
 * @author:@guo.zj
 */
@Service
public class CarServiceImpl implements CarService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VehicleStateService vehicleStateService;
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private RedisRepository redisRepository;
    @Override
    public JSONObject control(ControlRequestmeters data, UserInfo user) {
        List<Map<String, Double>> Location =new ArrayList<Map<String, Double>>();
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getAttributes().getVin();
            String cmdid=data.getData().getAttributes().getCmdid();
            int smsflag=data.getData().getAttributes().getSmsflag();
            //开关操作
            int onoff=data.getData().getAttributes().getOnoff();
            logger.info("操作:" + onoff);
            if(vin==null||"".equals(vin)||cmdid==null||"".equals(cmdid)){
                jsonObject.put("status",2002);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if("1001".equals(cmdid)||"1002".equals(cmdid)||"1005".equals(cmdid)||"1006".equals(cmdid)||"1007".equals(cmdid)){//1001 远程开关车门 1002天窗,1005闪灯鸣笛,1006后备箱,1007车辆启动/关闭
                //调用的接口参数准备
                EVvehicleCtrlRequestParameter eVvehicleCtrlRequestParameter=new EVvehicleCtrlRequestParameter();
                EVVehicleCtrl evVehicleCtrl=new EVVehicleCtrl();
                evVehicleCtrl.setAction(onoff);
                evVehicleCtrl.setVin(vin);
                evVehicleCtrl.setSmsflag(smsflag);
                eVvehicleCtrlRequestParameter.setData(evVehicleCtrl);
                //调用车辆控制接口（车辆控制接口在remoteService中）
                JSONObject remoteJSONObject=remoteService.tspControl(eVvehicleCtrlRequestParameter,cmdid,user);
                if(remoteJSONObject!=null) {
                    if((Integer) remoteJSONObject.get("status")==200){ //判断结果
                        jsonObject.put("status", 2000);
                        jsonObject.put("msg", "下发成功");
                        logger.info("返回的json:{}", jsonObject);
                    }else if ((Integer) remoteJSONObject.get("status")==301){
                        jsonObject.put("status", 2003);
                        jsonObject.put("msg", "指令操作有误");
                        logger.info("返回的json:{}", jsonObject);
                    }else{
                        jsonObject.put("status", 4000);
                        jsonObject.put("msg", "下发失败");
                        logger.info("返回的json:{}", jsonObject);
                    }
                }
            } else if("1004".equals(cmdid)) {
                //1004远程开空调
                //调用天窗操作的接口参数准备
                AirCondition airCondition = data.getData().getAttributes().getAirconditioner();
                AirConditionCtrl airConditionCtrl=new AirConditionCtrl();
                AirConditionRequestParameter airConditionRequestParameter=new AirConditionRequestParameter();
                airConditionCtrl.setAcparam(airCondition);
                airConditionCtrl.setCmdid(cmdid);
                airConditionCtrl.setVin(vin);
                airConditionRequestParameter.setData(airConditionCtrl);
                //调用空调控制接口（车辆控制接口在remoteService中）
                JSONObject remoteJSONObject = remoteService.airconditionerturn(airConditionRequestParameter,onoff,user);
                if (remoteJSONObject != null) {//因空调可以与车进行真实操作（车控操作）但是tsp提供的车辆状态没有空调状态的信息，根据产品经理要求将空调开启关闭状态存入app后台数据库
                    if ((Integer) remoteJSONObject.get("status") == 200) {
                        //存入数据库的key是vin+air，例如“XXXXXXXXXXXXair”
                        String vinair=vin+"air";
                        boolean flagRedis = redisRepository.set(vinair,onoff);
                        if (flagRedis) {
                            logger.info(flagRedis+"存入redis");
                        }else{
                            logger.info(flagRedis+"未存入redis");
                        }
                        jsonObject.put("status", 2000);
                        jsonObject.put("msg", "下发成功");
                        logger.info("返回的json:{}", jsonObject);
                    } else {
                        jsonObject.put("status", 4000);
                        jsonObject.put("msg", "下发失败");
                        logger.info("返回的json:{}", jsonObject);
                    }
                }
                //车窗接口
            }else if("1003".equals(cmdid)) {//因tsp没有任何关于车窗的功能，而app上有这个功能，所有关于车窗的功能全部都由APP后台进行处理
              String vinwindow=vin+"window";
                //存入数据库的key是vin+window，例如“XXXXXXXXXXXXwindow”
                boolean flagRedis = redisRepository.set(vinwindow,onoff);
                if (flagRedis) {
                    logger.info(flagRedis+"存入redis");
                    jsonObject.put("status", 2000);
                    jsonObject.put("msg", "车窗操作成功");
                    logger.info("返回的json:{}", jsonObject);
                }else{
                    logger.info(flagRedis+"未存入redis");
                    jsonObject.put("status", 4000);
                    jsonObject.put("msg", "车窗操作失败");
                    logger.info("返回的json:{}", jsonObject);
                }
            }else{
                jsonObject.put("status", 4000);
                jsonObject.put("msg", "下发失败,未找到指令");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",5000);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }

}