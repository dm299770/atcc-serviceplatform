package com.acv.cloud.services.message.impl;

import com.acv.cloud.domain.body.req.message.GetParams;
import com.acv.cloud.domain.body.resp.message.MessageResponse;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.app.NotificationResultConstants;
import com.acv.cloud.repository.mongotemplate.INotificationMongoDBDao;
import com.acv.cloud.services.message.MessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by liyang on 2019/01/10.
 */
@Service
public class MessageServiceImpl implements MessageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private INotificationMongoDBDao notificationMongoDBDao;

    @Override
    public JSONObject selectMessage(GetParams message) {
        JSONObject json = new JSONObject();
        JSONObject attributes = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            logger.info("请求体:" + message);

            String phoneNum = message.getData().getAttributes().getPhoneNum();
            String type = message.getData().getAttributes().getType(); //通知类型
            Integer pageNum = message.getData().getAttributes().getPageNum();
            Integer pageSize = message.getData().getAttributes().getPageSize();

            if (phoneNum == null || "".equals(phoneNum)) {
                json.put(AppResultConstants.STATUS, NotificationResultConstants.PHONE_EMPTY);
                json.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (type == null || "".equals(type)) {
                json.put(AppResultConstants.STATUS, NotificationResultConstants.TYPE_EMPTY);
                json.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if ("all".equals(type)) {
                //查全部类型通知
                List<MessageResponse> messageResponse = notificationMongoDBDao.queryList(phoneNum, type, pageSize, pageNum);
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
                json.put("data", messageResponse);
                attributes.put("attributes", json);
                attributes.put("type", "UserAccount");
                attributes.put("id", "1001192");
                data.put("data", attributes);
            } else if ("system".equals(type)) {
                //查系统类型
                List<MessageResponse> messageResponse = notificationMongoDBDao.queryList(phoneNum, type, pageSize, pageNum);
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
                json.put("data", messageResponse);
                attributes.put("attributes", json);
                attributes.put("type", "UserAccount");
                attributes.put("id", "1001192");
                data.put("data", attributes);
            } else if ("remind".equals(type)) {
                //查提醒类型
                List<MessageResponse> messageResponse = notificationMongoDBDao.queryList(phoneNum, type, pageSize, pageNum);
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
                json.put("data", messageResponse);
                attributes.put("attributes", json);
                attributes.put("type", "UserAccount");
                attributes.put("id", "1001192");
                data.put("data", attributes);
            } else if ("warn".equals(type)) {
                //查警告类型
                List<MessageResponse> messageResponse = notificationMongoDBDao.queryList(phoneNum, type, pageSize, pageNum);
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
                json.put("data", messageResponse);
                attributes.put("attributes", json);
                attributes.put("type", "UserAccount");
                attributes.put("id", "1001192");
                data.put("data", attributes);
            } else {
                json.put(AppResultConstants.STATUS, NotificationResultConstants.TYPE_ERROR);
                json.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            }
        } catch (Exception e) {
            json.put(AppResultConstants.STATUS, NotificationResultConstants.RETURN_ERROR);
            json.put(AppResultConstants.MSG, NotificationResultConstants.SEVER_ERROR);
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public JSONObject delMessage(String ids) {
        JSONObject json = new JSONObject();
        try {
            if (ids == null || ids.isEmpty()) {
                json.put(AppResultConstants.STATUS, NotificationResultConstants.ID_EMPTY);
                json.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else {
                notificationMongoDBDao.delMessage(ids);
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
            }
        } catch (Exception e) {
            json.put(AppResultConstants.STATUS, NotificationResultConstants.RETURN_ERROR);
            json.put(AppResultConstants.MSG, NotificationResultConstants.SEVER_ERROR);
            e.printStackTrace();
        }
        return json;
    }
}
