package com.acv.cloud.services.remote.impl;


import com.acv.cloud.jsonBean.remote.tspremotemodel.RemoteTspVehicle;
import com.acv.cloud.models.remote.Token;
import com.acv.cloud.models.remote.tspvehiclemodel.TspAirConditioner;
import com.acv.cloud.models.remote.tspvehiclemodel.TspVehicle;
import com.acv.cloud.services.http.HttpAPIService;
import com.acv.cloud.services.test.HttpAPITestService;
import com.acv.cloud.utils.HttpResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.AirConditionRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.EVWindowRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.EVvehicleCtrlRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.models.remote.AirCondition;
import com.acv.cloud.services.remote.RemoteService;
import org.omg.CORBA.StringHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description:车辆操控接口
 * @author:@guo.zj
 */
@Service
public class RemoteServiceImpl implements RemoteService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    String urlS="http://59.44.43.234:19002/remote/control";
    //String urlS="http://localhost:8885/test/tests";
    @Autowired
    private HttpAPIService httpAPIService;

    @Override
    public JSONObject remotedoor(EVvehicleCtrlRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1001";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            int action = data.getData().getAction();
            //Thread.sleep(2000);
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ( "NISSAN0000000000".equals(vin)&& action==0) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                RemoteTspVehicle remoteTspVehicle=new RemoteTspVehicle();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(0);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                System.out.println(result.getStatus());
                if (result.getStatus()==200) {
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);
            } else if("NISSAN0000000000".equals(vin)&& action==1) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(1);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==200) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                    logger.info("返回的json:{}", jsonObject);
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                    logger.info("返回的json:{}", jsonObject);
                }

            }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }

    @Override
    public JSONObject sunroof(EVvehicleCtrlRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1002";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            int action = data.getData().getAction();
            Thread.sleep(2000);
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ( "NISSAN0000000000".equals(vin)&& action==0) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                RemoteTspVehicle remoteTspVehicle=new RemoteTspVehicle();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(0);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);
            } else if("NISSAN0000000000".equals(vin)&& action==1) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                RemoteTspVehicle remoteTspVehicle=new RemoteTspVehicle();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(1);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                jsonObject.put("msg", "指令下发成功");
                logger.info("返回的json:{}", jsonObject);

            }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }

    @Override
    public JSONObject window(EVWindowRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1003";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            int action = data.getData().getAction();
            String wsite = data.getData().getWsite();
            Thread.sleep(2000);
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)) {
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data", "");
            }else if (!wsite.equals("FL")) {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }else if ( "NISSAN0000000000".equals(vin)&& action==0) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(0);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);
            } else if("NISSAN0000000000".equals(vin)&& action==1) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(1);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                jsonObject.put("msg", "指令下发成功");
                logger.info("返回的json:{}", jsonObject);

            }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }

    @Override
    public JSONObject airconditionertrun(AirConditionRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1004";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            int action = data.getData().getAction();
            AirCondition acparam=new AirCondition();
            acparam= data.getData().getAcparam();
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ( "NISSAN0000000000".equals(vin)) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                TspAirConditioner tspAirConditioner=new TspAirConditioner();
                tspAirConditioner.setCmdid(cmdid);
                tspAirConditioner.setOnoff(action);
                tspAirConditioner.setRequestid(requestid);
                tspAirConditioner.setVin(vin);
                tspAirConditioner.setAirconditioner(acparam);
                tspObject.put("data",tspAirConditioner );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }

                logger.info("返回的json:{}", jsonObject);
            } else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }


    @Override
    public JSONObject horn(VinRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1005";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            Thread.sleep(2000);
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ( "NISSAN0000000000".equals(vin)) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                RemoteTspVehicle remoteTspVehicle=new RemoteTspVehicle();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(1);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);
            }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }

    @Override
    public JSONObject runk(EVvehicleCtrlRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1006";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            int action = data.getData().getAction();
            Thread.sleep(2000);
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
            }else if ( "NISSAN0000000000".equals(vin)&& action==0) {
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==200) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);
            } else if("NISSAN0000000000".equals(vin)&& action==1) {
                HttpResult result =httpAPIService.doPost(urlS,tspObject);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
                logger.info("返回的json:{}", jsonObject);

            }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }

    @Override
    public JSONObject carpower(EVvehicleCtrlRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        JSONObject tspObject = new JSONObject();
        String cmdid="1007";
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            int action = data.getData().getAction();
            int smsflag= data.getData().getSmsflag();
            JSONArray jsonArray = new JSONArray();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ( "NISSAN0000000000".equals(vin)&& action==0) {
                  UUID uuid = UUID.randomUUID();
                  String requestid=uuid.toString();
                RemoteTspVehicle remoteTspVehicle=new RemoteTspVehicle();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(0);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                jsonObject.put("status", 200);
                jsonObject.put("msg", "指令下发成功");
                logger.info("返回的json:{}", jsonObject);
            } else if("NISSAN0000000000".equals(vin)&& action==1) {
                UUID uuid = UUID.randomUUID();
                String requestid=uuid.toString();
                RemoteTspVehicle remoteTspVehicle=new RemoteTspVehicle();
                TspVehicle tspVehicle=new TspVehicle();
                tspVehicle.setCmdid(cmdid);
                tspVehicle.setOnoff(1);
                tspVehicle.setRequestid(requestid);
                tspVehicle.setVin(vin);
                tspObject.put("data",tspVehicle );
                String json= tspObject.toJSONString();
                System.out.println(json);
                HttpResult result =httpAPIService.doPosts(urlS,json);
                if (result.getStatus()==302) {
                    jsonObject.put("status", 200);
                    jsonObject.put("msg", "指令下发成功");
                } else {
                    jsonObject.put("status", 400);
                    jsonObject.put("msg", "指令下发失败");
                }
            }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                logger.info("返回的json:{}",jsonObject);
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
        }
        return jsonObject;
    }
}