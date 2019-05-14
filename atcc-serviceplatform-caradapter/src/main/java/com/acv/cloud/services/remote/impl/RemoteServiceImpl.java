package com.acv.cloud.services.remote.impl;


import com.acv.cloud.domain.model.car.control.AirCondition;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.jsonBean.remote.parameter.AirConditionRequestParameter;
import com.acv.cloud.jsonBean.remote.parameter.EVvehicleCtrlRequestParameter;
import com.acv.cloud.models.tspvehiclemodel.TspAirConditioner;
import com.acv.cloud.models.tspvehiclemodel.TspVehicle;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.http.HttpAPIService;
import com.acv.cloud.utils.HttpResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.services.remote.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @description:车辆操控接口
 * @author:@guo.zj
 */
@Service
public class RemoteServiceImpl implements RemoteService {
    @Value("${http.urlControlTSP}")
    private String urlTSP;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //String urlTSP="http://59.44.43.234:19002/remote/control";
    //String urlTSP="http://localhost:8885/test/tests";
    @Autowired
    private HttpAPIService httpAPIService;
    @Autowired
    private RedisRepository redisRepository;
    //为requestid临时使用
    int i=0;

    //空调开关
    @Override
    public JSONObject airconditionerturn(AirConditionRequestParameter data, int onoff, UserInfo user) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        Boolean flag=true;
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            String cmdid=data.getData().getCmdid();
            AirCondition acparam=new AirCondition();
            acparam= data.getData().getAcparam();
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)||cmdid==null||"".equals(cmdid)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if (flag) {
                String requestid="";
                if(i>0&&i<=65535){
                    requestid=String.valueOf(i);
                    i++;
                }else{
                    i=1;
                    requestid=String.valueOf(i);
                    i++;
                }
                TspAirConditioner tspAirConditioner=new TspAirConditioner();
                tspAirConditioner.setCmdid(cmdid);
                tspAirConditioner.setRequestid(requestid);
                tspAirConditioner.setVin(vin);
                tspAirConditioner.setOnoff(onoff);
                tspAirConditioner.setAirconditioner(acparam);
                tspObject.put("data",tspAirConditioner );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlTSP,json);
                if (result.getStatus()==200) {
                    String phonenum = user.getPhoneNum();
                    logger.info("获取的手机号为：" + phonenum);
                    boolean flagRedis = redisRepository.set(requestid, phonenum);
                    if (flagRedis) {
                        logger.info(phonenum+"存入redis");
                    }else{
                        logger.info(phonenum+"未存入redis");
                    }

                    jsonObject.put("status", 2000);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 4000);
                    jsonObject.put("msg", "指令下发失败");
                }

                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 4000);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",5000);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }

    //启车 闪灯鸣笛 后备箱 车窗 天窗 门锁
    @Override
    public JSONObject tspControl(EVvehicleCtrlRequestParameter data, String cmdid, UserInfo user) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        try {
            int action=-1;
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            action = data.getData().getAction();
            JSONArray jsonArray = new JSONArray();
            //生成请求ID 根据tsp要求requestid为String类型的数字，后期将规范requestid
            if (action==0) {
                String requestid="";
                if(i>0&&i<=65535){
                    requestid=String.valueOf(i);
                    i++;
                }else{
                    i=1;
                    requestid=String.valueOf(i);
                    i++;
                }
                //准备tsp所要的参数
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(0);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                logger.info("tsp的json:" + json);
                //将参数传给tsp
                HttpResult result =httpAPIService.doPosts(urlTSP,json);
                logger.info("tsp返回的状态码:" +result.getStatus());
                if (result.getStatus()==200) {
                    String phonenum = user.getPhoneNum();
                    logger.info("获取的手机号为：" + phonenum);
                    //将手机号存入数据库中目的是回调接口向手机推送通知要使用手机号，暂时使用redis，之后改用mongdb
                    boolean flagRedis = redisRepository.set(requestid, phonenum);
                    if (flagRedis) {
                        logger.info(phonenum+"存入redis");
                    }else{
                        logger.info(phonenum+"未存入redis");
                    }
                    jsonObject.put("status", 2001);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status",2004);
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);
            } else if(action==1) {
                //  UUID uuid = UUID.randomUUID();
                //  String requestid=uuid.toString();
                String requestid="";
                //生成请求ID 根据tsp要求requestid为String类型的数字，后期将规范requestid
                if(i>0&&i<=65535){
                    requestid=String.valueOf(i);
                    i++;
                }else{
                    i=1;
                    requestid=String.valueOf(i);
                    i++;
                }
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(1);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlTSP,json);
                if (result.getStatus()==200) {
                    String phonenum=user.getPhoneNum();
                    redisRepository.set(requestid,phonenum);
                    jsonObject.put("status", 2001);
                    jsonObject.put("msg", "指令下发成功");
                    logger.info("返回的json:{}", jsonObject);
                } else {
                    jsonObject.put("status", 2004);
                    jsonObject.put("msg", "指令下发失败");
                    logger.info("返回的json:{}", jsonObject);
                }

            }else {
                jsonObject.put("status", 2003);
                jsonObject.put("msg", "空调开关操作指令有误");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",5000);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }
}