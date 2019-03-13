package com.acv.cloud.services.callback.impl;

import com.acv.cloud.jsonBean.finder.myCarFinder;
import com.acv.cloud.jsonBean.remote.RemoteCallBackParameters;
import com.acv.cloud.services.callback.RemoteCallBackService;
import com.alibaba.fastjson.JSONObject;
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
public class RemoteCallBackServiceImpl implements RemoteCallBackService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public JSONObject remoteCallBack(RemoteCallBackParameters data) {
        List<Map<String, Double>> Location =new ArrayList<Map<String, Double>>();
        myCarFinder mycarFinder=new myCarFinder();
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            String cmdid = data.getData().getCmdid();
            String requestid=data.getData().getRequestid();
            if(vin==null||"".equals(vin)){
                jsonObject.put("status", 400);
                jsonObject.put("msg", "上报失败，vin为空");
            }else if(cmdid==null||"".equals(cmdid)){
                jsonObject.put("status", 400);
                jsonObject.put("msg", "上报失败，cmdid为空");
            }else if (requestid==null||"".equals(requestid)) {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "上报失败，requestid为空");
            }else{
                jsonObject.put("status", 200);
                jsonObject.put("msg", "上报成功");
                logger.info("返回的json:{}", jsonObject);
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