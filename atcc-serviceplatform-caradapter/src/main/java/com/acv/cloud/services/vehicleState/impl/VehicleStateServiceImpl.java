package com.acv.cloud.services.vehicleState.impl;


import com.acv.cloud.models.vehiclestate.*;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.finder.myCarFinder;
import com.acv.cloud.jsonBean.remote.RequestParameters;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.DeleteDataRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.VehicleState;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.VehicleStateRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.WriteVehicleStateRequestParameter;
import com.acv.cloud.services.vehicleState.VehicleStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:远程寻车接口实现
 * @author:@guo.zj
 */
@Service
public class VehicleStateServiceImpl implements VehicleStateService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisRepository redisRepository;

    int i=0;
    @Override
    public JSONObject vehiclestate(RequestParameters data) {
        List<Map<String, Double>> Location =new ArrayList<Map<String, Double>>();
        myCarFinder mycarFinder=new myCarFinder();
        JSONObject jsonObject = new JSONObject();
        int endurancemileage=0;
        String endurancemileages="";
        int mileage=0;
        String oilconsumption="";
        String roadhau="";
        String drivingstate="行驶中";
        String fcphk="";
        String tire="";
        try {
            logger.info("请求体:" + data);
            //Finder finder = JSON.parseObject(request, FinderRequestParameter.class).getData();
            String vin = data.getData().getVin();
            String serviceType = data.getData().getType();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("APP".equals(serviceType) && "NISSAN0000000000".equals(vin)||"10".equals(vin)) {
                VehicleState vehicleState=new VehicleState();
                if(i==0){
                    endurancemileage=435;
                    endurancemileages="435Km";
                    mileage=800;
                    oilconsumption="62%";
                    roadhau="16990Km";
                    drivingstate="行驶中";
                    fcphk="8.5L";
                    tire = "0";
                    i++;
                }else{
                    endurancemileage=430;
                    endurancemileages="430Km";
                    mileage=800;
                    oilconsumption="61%";
                    roadhau="16995Km";
                    drivingstate="行驶中";
                    fcphk="8.1L";
                    tire = "1";
                    i--;
                }
                NumberFormat numberFormat = NumberFormat.getInstance();
                // 设置精确到小数点后2位
                numberFormat.setMaximumFractionDigits(2);
                String result = numberFormat.format( (float) endurancemileage / (float) mileage * 100);
                double ss=Double.parseDouble(result);
                double aa= Math.round(ss);
                int num = (int)aa;
                vehicleState.setPartmileage(num);
                vehicleState.setEndurancemileage(endurancemileages);
                vehicleState.setDrivingstate(drivingstate);
                vehicleState.setFcphk(fcphk);
                vehicleState.setMileage(mileage);
                vehicleState.setOilconsumption(oilconsumption);
                vehicleState.setRoadhaul(roadhau);
                vehicleState.setTire(tire);
                jsonObject.put("status", 200);
                jsonObject.put("msg", "success");
                jsonObject.put("data",vehicleState);
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data","");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }



    @Override
    public JSONObject deletedata(DeleteDataRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            String serviceType = data.getData().getType();
            String controlType = data.getData().getControltype();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("APP".equals(serviceType) && "NISSAN0000000000".equals(vin)||"10".equals(vin)) {
                VehicleState vehicleState=new VehicleState();
                if("vehicle".equals(controlType)){
                    jsonObject.put("msg", "重置车辆数据成功");
                }else if("person".equals(controlType)){
                    jsonObject.put("msg", "清除人员数据成功");
                }
                jsonObject.put("status", 200);
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data","");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }

    @Override
    public JSONObject EVvehiclestate(VehicleStateRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        Float resbatterycap;
        String drivingstatus="";
        String chargingstatus="";
        int trunkstatus=0;
        int doorstatus=0;
        int sunroofstatus=0;
        String requestid="";
        long updatetime;
        Float usablekm;
        Float consumerate;
        Tirepressure tirepressure=new  Tirepressure();
        WindowStatus windowstatus=new WindowStatus();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("NISSAN0000000000".equals(vin)||"10".equals(vin)) {
                EVVehicleState vehicleState=new EVVehicleState();
                resbatterycap=0.60F;
                drivingstatus="Parking";
                chargingstatus="Charging";
                trunkstatus=0;
                doorstatus=0;
                sunroofstatus=0;
                requestid="123456";
                usablekm=288F;
                consumerate=15.6F;
                updatetime=System.currentTimeMillis()*1000;
                tirepressure.setBL(2.6F);
                tirepressure.setBR(2.5F);
                tirepressure.setFL(2.6F);
                tirepressure.setFR(1.8F);
                tirepressure.setBlstate(0);
                tirepressure.setBrstate(0);
                tirepressure.setFlstate(0);
                tirepressure.setFrstate(1);
                windowstatus.setBL(0);
                windowstatus.setFL(0);
                windowstatus.setFR(0);
                windowstatus.setBR(0);
                vehicleState.setWindowstatus(windowstatus);
                vehicleState.setTirepressure(tirepressure);
                vehicleState.setDrivingstatus(drivingstatus);
                vehicleState.setChargingstatus(chargingstatus );
                vehicleState.setDoorstatus(doorstatus);
                vehicleState.setRequestid(requestid);
                vehicleState.setResbatterycap(resbatterycap);
                vehicleState.setTrunkstatus(trunkstatus);
                vehicleState.setSunroofstatus(sunroofstatus);
                vehicleState.setUpdatetime(updatetime);
                vehicleState.setUsablekm(usablekm);
                vehicleState.setConsumerate(consumerate);
                jsonObject.put("status", 200);
                jsonObject.put("msg", "success");
                jsonObject.put("data",vehicleState);
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data","");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }

    @Override
    public JSONObject airConditionState(VinRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("NISSAN0000000000".equals(vin)||"10".equals(vin)) {
                AirConditionStatus airConditionStatus=new AirConditionStatus();
                airConditionStatus.setOnoff(1);
                airConditionStatus.setModel(1);
                airConditionStatus.setInternaltemperture(25F);
                airConditionStatus.setTemperturel(25F);
                airConditionStatus.setTemperturer(19F);
                airConditionStatus.setWindowspeed(3);
                airConditionStatus.setDefrost(0);
                airConditionStatus.setTiming(0);
                jsonObject.put("status", 200);
                jsonObject.put("msg", "success");
                jsonObject.put("data",airConditionStatus);
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data","");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }

    @Override
    public JSONObject ALLvehiclestate(VinRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        ALLVehicleState vehicleState = new ALLVehicleState();
        String vin=data.getData().getVin();
        try {
            logger.info("请求体:" + data);
            if (vin == null || "".equals(vin)) {
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data", "");
            } else if ("NISSAN0000000000".equals(vin)||"10".equals(vin)) {
                vehicleState=this.getvehiclestate(vin);
                vehicleState.setVin(vin);
                jsonObject.put("status", 200);
                jsonObject.put("msg", "success");
                jsonObject.put("data", vehicleState);
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data", "");
                logger.info("返回的json:{}", jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status", 500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data", "");
        }
        return jsonObject;
    }

    @Override
    public JSONObject Writevehiclestate(WriteVehicleStateRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        Float resbatterycap;
        String drivingstatus="";
        String chargingstatus="";
        int trunkstatus=0;
        int doorstatus=0;
        int sunroofstatus=0;
        String requestid="";
        long updatetime;
        Float usablekm;
        Float consumerate;
        Tirepressure tirepressure=new  Tirepressure();
        WindowStatus windowstatus=new WindowStatus();
        Float maxkm=0F;
        Float resoilcap=0F;
        Float roadhaul=0F;
        Float fcphk=0F;
        String diagnosedate="";
        String vehicletype="";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("NISSAN0000000000".equals(vin)||"10".equals(vin)) {
                ALLVehicleState vehicleState=new ALLVehicleState();
                VehicleCategory vehicleCategory=new VehicleCategory();
                resbatterycap=data.getData().getResbatterycap();
                drivingstatus=data.getData().getDrivingstatus();
                chargingstatus=data.getData().getChargingstatus();
                trunkstatus=data.getData().getTrunkstatus();
                doorstatus=data.getData().getDoorstatus();
                sunroofstatus=data.getData().getSunroofstatus();
                requestid="123456";
                usablekm=data.getData().getUsablekm();
                consumerate=data.getData().getConsumerate();
                updatetime=System.currentTimeMillis()*1000;
                if(data.getData().getTirepressure()!=null) {
                    tirepressure.setBL(data.getData().getTirepressure().getBL());
                    tirepressure.setBR(data.getData().getTirepressure().getBR());
                    tirepressure.setFL(data.getData().getTirepressure().getFL());
                    tirepressure.setFR(data.getData().getTirepressure().getFR());
                    tirepressure.setBlstate((data.getData().getTirepressure().getBlstate()));
                    tirepressure.setBrstate((data.getData().getTirepressure().getBrstate()));
                    tirepressure.setFlstate((data.getData().getTirepressure().getFlstate()));
                    tirepressure.setFrstate((data.getData().getTirepressure().getFrstate()));
                }
                if(data.getData().getWindowstatus()!=null) {
                    windowstatus.setBL(data.getData().getWindowstatus().getBL());
                    windowstatus.setFL(data.getData().getWindowstatus().getFL());
                    windowstatus.setFR(data.getData().getWindowstatus().getFR());
                    windowstatus.setBR(data.getData().getWindowstatus().getBR());
                }
                maxkm=data.getData().getMaxkm();
                resoilcap=data.getData().getResoilcap();
                roadhaul=data.getData().getRoadhaul();
                fcphk=data.getData().getFcphk();
                diagnosedate=data.getData().getDiagnosedate();
                if(data.getData().getVehicleCategory()!=null) {
                    vehicletype = data.getData().getVehicleCategory().getType();
                }
                vehicleCategory.setType(vehicletype);
                vehicleState.setVehicleCategory(vehicleCategory);
                vehicleState.setMaxkm(maxkm);
                vehicleState.setResoilcap(resoilcap);
                vehicleState.setRoadhaul(roadhaul);
                vehicleState.setFcphk(fcphk);
                vehicleState.setDiagnosedate(diagnosedate);
                vehicleState.setWindowstatus(windowstatus);
                vehicleState.setTirepressure(tirepressure);
                vehicleState.setDrivingstatus(drivingstatus);
                vehicleState.setChargingstatus(chargingstatus );
                vehicleState.setDoorstatus(doorstatus);
                vehicleState.setRequestid(requestid);
                vehicleState.setResbatterycap(resbatterycap);
                vehicleState.setTrunkstatus(trunkstatus);
                vehicleState.setSunroofstatus(sunroofstatus);
                vehicleState.setUpdatetime(updatetime);
                vehicleState.setUsablekm(usablekm);
                vehicleState.setConsumerate(consumerate);
                vehicleState.setVin(vin);
                this.updateVehicleState(vin ,vehicleState);
                jsonObject.put("status", 200);
                jsonObject.put("msg", "success");
                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data","");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }

    //从redis里获取车辆状态
    public ALLVehicleState getvehiclestate(String vin) {
           Object object =redisRepository.get(vin);

           ALLVehicleState allVehicleState= new ALLVehicleState();
           allVehicleState=(ALLVehicleState)object;
           return allVehicleState;
    }

    public void updateVehicleState(String vin, ALLVehicleState allVehicleState) {
        redisRepository.remove(vin);
        redisRepository.set(vin, allVehicleState);
    }
}