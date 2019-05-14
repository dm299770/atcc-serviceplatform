package com.acv.cloud.services.callback.impl;

import com.acv.cloud.fegin.ua.message.MessageFegin;
import com.acv.cloud.jsonBean.finder.myCarFinder;
import com.acv.cloud.jsonBean.remote.RemoteCallBackParameters;
import com.acv.cloud.models.fegin.Datal;
import com.acv.cloud.models.fegin.Notification;
import com.acv.cloud.models.fegin.NotificationParams;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.callback.RemoteCallBackService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private MessageFegin messageFegin;

    @Override
    public JSONObject remoteCallBack(RemoteCallBackParameters data) {
        List<Map<String, Double>> Location = new ArrayList<Map<String, Double>>();
        myCarFinder mycarFinder = new myCarFinder();
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("请求体:" + data);
            String vin = data.getData().getVin();
            String cmdid = data.getData().getCmdid();
            String requestid = data.getData().getRequestid();
            if (vin == null || "".equals(vin)) {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "上报失败，vin为空");
            } else if (cmdid == null || "".equals(cmdid)) {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "上报失败，cmdid为空");
            } else if (requestid == null || "".equals(requestid)) {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "上报失败，requestid为空");
            } else {
//                String beforeNum = (String) redisRepository.get(data.getData().getRequestid());
                String phonenum = "[" + "17755306716" + "]";
                int result = data.getData().getResult();
                String cntextresult = "";
                if (result == 1) {
                    cntextresult = "成功";
                } else {
                    cntextresult = "失败";
                }
                String context = "可爱的雷诺用户,您的操作" + cntextresult + "啦";
                logger.info(phonenum + "/" + context);
                Notification notification = new Notification(null, null, "操作结果", context, phonenum, "remind", vin, null, null, null, null);
                Datal datal = new Datal(notification);
                NotificationParams params = new NotificationParams(datal);
                JSONObject message = messageFegin.pushMsgList(params);
                if (message != null) {
                    logger.info("推送的结果:" + message.get("msg").toString());
                }
                logger.info("推送的结果:失败");
                jsonObject.put("status", 200);
                jsonObject.put("msg", "上报成功");
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

}