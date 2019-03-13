package com.acv.cloud.services.finder.impl;

import com.acv.cloud.jsonBean.finder.request.SendToCarRequestParameter;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.finder.myCarFinder;
import com.acv.cloud.jsonBean.finder.request.PathRequestParameter;
import com.acv.cloud.jsonBean.remote.RequestParameters;
import com.acv.cloud.services.finder.FinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:远程寻车接口实现
 * @author:@guo.zj
 */
@Service
public class FinderServiceImpl implements FinderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public JSONObject finder(RequestParameters data) {
        List<Map<String, Double>> Location =new ArrayList<Map<String, Double>>();
        myCarFinder mycarFinder=new myCarFinder();
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            //Finder finder = JSON.parseObject(request, FinderRequestParameter.class).getData();
            String vin = data.getData().getVin();
            String serviceType = data.getData().getType();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("APP".equals(serviceType) && "NISSAN0000000000".equals(vin)) {
                //Double longitude =121.4605567391;
                //Double latitude = 31.2175575048;
                jsonObject.put("status", 200);
                jsonObject.put("msg", "success");
                jsonObject.put("data",mycarFinder);
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
    public JSONObject sendToCar(SendToCarRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            if (1==1) {
                Double longitude =121.4605567391;
                Double latitude = 31.2175575048;
                Thread.sleep(900);
                jsonObject.put("status", 200);
                jsonObject.put("msg", "发送到车成功");
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
    public JSONObject path(PathRequestParameter request) {
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + request);
            if (1==1) {
                Thread.sleep(900);
                jsonObject.put("status", 200);
                jsonObject.put("msg", " 发送到车成功");
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

}