package com.acv.cloud.services.vehicleState.impl;


import com.acv.cloud.jsonBean.car.TspData;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.VehicleStateRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestatemodel.RequestVehicleState;
import com.acv.cloud.models.appvehiclestate.*;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.http.HttpAPIService;
import com.acv.cloud.services.vehicleState.VehicleStateService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @description:远程寻车接口实现
 * @author:@guo-.zj
 */
@Service
public class VehicleStateServiceImpl implements VehicleStateService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisRepository redisRepository;
    @Autowired


    @Value("${http.urlStateTSP}")
    private String urlTSP;
    @Autowired
    private HttpAPIService httpAPIService;
    int i = 0;

    @Override
    public JSONObject EVvehiclestate(String vin, int mode) {
        JSONObject jsonObject = new JSONObject();
        boolean flag = true;
        Tirepressure tirepressure = new Tirepressure();
        WindowStatus windowstatus = new WindowStatus();
        try {
            logger.info("请求体:" + vin +"|"+mode);
            if (vin == null || "".equals(vin)) {
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data", "");
            } else if (flag) {
                JSONObject tspObject = new JSONObject();
                //向tsp传参数做准备
                RequestVehicleState requestVehicleState = new RequestVehicleState();
                requestVehicleState.setMode(mode);
                requestVehicleState.setVin(vin);
                //类转json
                tspObject.put("data", requestVehicleState);
                //形成json串String
                String json = tspObject.toJSONString();
                //将参数传给tsp
                String tspJson = httpAPIService.sendPosts(urlTSP, json);
                //接tsp传来的结果
                TspData tspData = new TspData();
                tspData = JSONObject.parseObject(tspJson, TspData.class);
                //将tsp传来的结果转到传给app的类
                EVVehicleState vehicleState = new EVVehicleState();
                if (tspData != null && tspData.getData() != null) {
                    //充电状态
                    vehicleState.setChargingStatus(tspData.getData().getChargingstatus());
                    //胎压 暂无胎压值
                    Tirepressure tirepressures = new Tirepressure();
                    if (tspData.getData().getTirepressure() != null && tspData.getData().getTirepressure().getBR() == 1) {
                        tirepressures.setRr(1F);
                        tirepressures.setRrstate(1);
                    } else {
                        tirepressures.setRr(0F);
                        tirepressures.setRrstate(0);
                    }
                    if (tspData.getData().getTirepressure() != null && tspData.getData().getTirepressure().getFL() == 1) {
                        tirepressures.setFl(1F);
                        tirepressures.setFlstate(1);
                    } else {
                        tirepressures.setFl(0F);
                        tirepressures.setFlstate(0);
                    }
                    if (tspData.getData().getTirepressure() != null && tspData.getData().getTirepressure().getFR() == 1) {
                        tirepressures.setFr(1F);
                        tirepressures.setFrstate(1);
                    } else {
                        tirepressures.setFr(0F);
                        tirepressures.setFrstate(0);
                    }
                    if (tspData.getData().getTirepressure() != null && tspData.getData().getTirepressure().getBL() == 1) {

                        tirepressures.setRl(1F);
                        tirepressures.setRlstate(1);
                    } else {
                        tirepressures.setRl(0F);
                        tirepressures.setRlstate(0);
                    }
                    vehicleState.setTirePressure(tirepressures);
                    //车门
                    String status = tspData.getData().getDoorstatus();
                    DoorStatus doorStatus = new DoorStatus();
                    if (status != null && "1,1,1,1".equals(status)) {
                        doorStatus.setFl(1);
                        doorStatus.setFr(1);
                        doorStatus.setRr(1);
                        doorStatus.setRl(1);

                    } else if (status != null && "0,0,0,0".equals(status)) {
                        doorStatus.setFl(0);
                        doorStatus.setFr(0);
                        doorStatus.setRr(0);
                        doorStatus.setRl(0);
                    } else {
                        doorStatus.setFl(-1);
                        doorStatus.setFr(-1);
                        doorStatus.setRr(-1);
                        doorStatus.setRl(-1);
                    }
                    vehicleState.setDoorStatus(doorStatus);
                    //剩余电量
                    float resbatterycap = tspData.getData().getResbatterycap();
                    float resbatterycaphunderd = resbatterycap / 100;
                    vehicleState.setCurrentBattery(resbatterycaphunderd);
                    //驾驶状态
                    vehicleState.setDrivingStatus(tspData.getData().getDrivingstatus());
                    //后备箱
                    vehicleState.setTrunkStatus(tspData.getData().getTrunkstatus());
                    //车窗  TSP上不来信号 假的（取的是app后台数据库的信息）
                    String vinwindow = vin + "window";
                    Object window = redisRepository.get(vinwindow);
                    int onoff = 0;
                    if (window != null) {
                        onoff = (Integer) redisRepository.get(vinwindow);
                    }
                    windowstatus.setRl(-1);
                    windowstatus.setRr(-1);
                    windowstatus.setFl(onoff);
                    windowstatus.setFr(-1);
                    vehicleState.setWindowStatus(windowstatus);
                    //天窗
                    vehicleState.setSunroofStatus(tspData.getData().getSunroofstatus());
                    //百公里续航
                    vehicleState.setUsableMileage(tspData.getData().getUsablekm());
                    //百公里耗电
                    vehicleState.setConsumeRate(tspData.getData().getConsumerate());
                    //当前时间
                    Date date = new Date();
                    long currtime = date.getTime();
                    vehicleState.setUploadTime(currtime);
                    //经纬度计算
                    double Lat = tspData.getData().getLat();
                    double Lon = tspData.getData().getLon();
                    vehicleState.setLat(Lat);
                    vehicleState.setLon(Lon);
                    //更新Redis缓存
                    this.updateVehicleState(vin, vehicleState);
                    jsonObject.put("status", 2000);
                    jsonObject.put("msg", "success");
                    jsonObject.put("data", vehicleState);
                    logger.info("返回的json:{}", jsonObject);
                } else {
                    jsonObject.put("status", 4000);
                    jsonObject.put("msg", "failed");
                    jsonObject.put("data", "");
                    logger.info("返回的json:{}", jsonObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status", 5000);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data", "");
        }
        return jsonObject;
    }

    @Override
    public JSONObject airConditionState(VinRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            if (vin == null || "".equals(vin)) {
                jsonObject.put("status", 2001);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data", "");
            } else if (0 == 0) {
                AirConditionStatus airConditionStatus = new AirConditionStatus();
                String vinair = vin + "air";
                int onoff = 0;
                if (redisRepository.get(vinair) != null) {
                    onoff = (Integer) redisRepository.get(vinair);
                }
                airConditionStatus.setOnoff(onoff);
                if (onoff == 1) {
                    airConditionStatus.setModel(1);
                    airConditionStatus.setInternaltemperture(25F);
                    airConditionStatus.setTemperturel(25F);
                    airConditionStatus.setTemperturer(19F);
                    airConditionStatus.setWindowspeed(3);
                    airConditionStatus.setDefrost(0);
                    airConditionStatus.setTiming(0);
                } else {
                    airConditionStatus.setModel(1);
                    airConditionStatus.setInternaltemperture(25F);
                    airConditionStatus.setTemperturel(25F);
                    airConditionStatus.setTemperturer(19F);
                    airConditionStatus.setWindowspeed(2);
                    airConditionStatus.setDefrost(0);
                    airConditionStatus.setTiming(0);
                }
                jsonObject.put("status", 2000);
                jsonObject.put("msg", "success");
                jsonObject.put("data", airConditionStatus);
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 4000);
                jsonObject.put("msg", "failed");
                jsonObject.put("data", "");
                logger.info("返回的json:{}", jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status", 5000);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data", "");
        }
        return jsonObject;
    }


    //从redis里获取车辆状态
    public EVVehicleState getvehiclestate(String vin) {
        Object object =redisRepository.get(vin);

        EVVehicleState eVVehicleState= new EVVehicleState();
        eVVehicleState=(EVVehicleState)object;
        return eVVehicleState;
    }

    public void updateVehicleState(String vin, EVVehicleState vehicleState) {
        redisRepository.remove(vin);
        redisRepository.set(vin, vehicleState);
    }
}