package com.acv.cloud.services.notification.impl;

import com.acv.cloud.domain.body.req.notification.NotificationParams;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.app.NotificationResultConstants;
import com.acv.cloud.frame.DateUtil;
import com.acv.cloud.mapper.user.TsUserMapper;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.repository.mongotemplate.INotificationMongoDBDao;
import com.acv.cloud.repository.redistemplate.INotificationDao;
import com.acv.cloud.services.notification.NotificationService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liyang on 2018/12/18.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${secretKey}")
    private String secretKey;

    @Value("${accessId}")
    private Long accessId;

    @Autowired
    private INotificationDao notificationDao;

    @Autowired
    private INotificationMongoDBDao notificationMongoDBDao;

    @Autowired
    private TsUserMapper tsUserMapper;

    @Override
    public JSONObject pushMsgDeviceList(NotificationParams no) {
        JSONObject obj = new JSONObject();
        try {
            logger.info("MessageController: push params phoneNum:" + no.getData().getAttributes().getPhoneNum() +
                    ",context:" + no.getData().getAttributes().getContext() + ",type:" + no.getData().getAttributes().getType() +
                    ",title:" + no.getData().getAttributes().getTitle());
            String phoneNum = no.getData().getAttributes().getPhoneNum();
            String title = no.getData().getAttributes().getTitle();
            String context = no.getData().getAttributes().getContext();
            String vin = no.getData().getAttributes().getVin();
            String type = no.getData().getAttributes().getType();
            String ids = UUID.randomUUID().toString().replaceAll("-", "");
            String imageURL = no.getData().getAttributes().getImageURL();

            if (phoneNum == null || "".equals(phoneNum)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.PHONE_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (title == null || "".equals(title)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.TITLE_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (context == null || "".equals(context)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.CONTENT_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (type == null || "".equals(type)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.TYPE_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else {
                XingeApp xinge = new XingeApp(accessId, secretKey);
                MessageIOS mess = pushIOSClient(title, context, type, imageURL);
                //未读标识符，未读
                Integer readflag = 0;
                //设置时间格式
                String createDate = DateUtil.getDate("yyyy/MM/dd HH:mm:ss");
                //json格式转换
                JSONArray jsonArray = JSONArray.parseArray(phoneNum);
                List<String> phoneNumList = jsonArray.toJavaList(String.class);
                logger.info("用户账号信息列表:" + phoneNumList);
                for (String phoneNums : phoneNumList) {
                    logger.info("当前准备推送的账户:" + phoneNums);
                    //手机号去查uesr_id
                    TsUser userId = tsUserMapper.findUserId(phoneNums);

//
//                    String device_account = String.format(RedisConstants.DEVICE_ACCOUNT + ":%s:%s", "IOS");
//
//                    String account_device = String.format(RedisConstants.ACCOUNT_DEVICE + ":%s:%s", userId.getUserId(), "IOS");

                    //用user_id去redis里面判断device_id(token)和设备类型
                    String deviceId = notificationDao.getDeviceToken(userId.getUserId());
                    logger.info("用户userId" + userId.getUserId());

                    //用device_id去判断是否是当前设备一致的deviceAccount(user_id)
                    String deviceAccount = notificationDao.getDeviceAccont(deviceId);

                    //截取deviceId
                    // String token = deviceId.substring(1, deviceId.length() - 1).replaceAll(" +", "").trim();
                    String token = deviceId.replaceAll("[^(0-9)(A-Za-z)]", "").trim();
                    logger.info("设备Id:" + deviceId);

                    //数据一致再推送
                    if (deviceAccount.equals(userId.getUserId())) {
                        //推送
                        String returnCode = xinge.pushSingleDevice(token, mess, XingeApp.IOSENV_PROD).toString();
                        //判断返回状态码是否推送成功
                        if (returnCode.contains("ret_code")) {
                            //{"err_msg":"无效帐号，请检查后重试","ret_code":48}
                            JSONObject returnCodeJson = JSONObject.parseObject(returnCode);
                            String returnCodeString = String.valueOf(returnCodeJson.get("ret_code"));
                            if ("0".equals(returnCodeString)) {
                                obj.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                                obj.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
                                //把推送消息插入mongodb
                                notificationMongoDBDao.insertList(ids, phoneNums, token, title, vin, context, createDate, userId, type, readflag, imageURL);
                            } else {
                                obj.put(AppResultConstants.MSG, NotificationResultConstants.RETURN_CODE_ERROR);
                                obj.put(AppResultConstants.STATUS, NotificationResultConstants.RETURN_ERROR);
                                logger.info("返回状态码:" + returnCodeString);
                            }
                        } else {
                            obj.put(AppResultConstants.STATUS, NotificationResultConstants.RESULT_ERROR);
                            obj.put(AppResultConstants.MSG, NotificationResultConstants.RETURN_CODE_ERROR);
                            logger.info("推送返回状态码:" + returnCode);
                        }
                    } else {
                        obj.put(AppResultConstants.STATUS, NotificationResultConstants.DEVICEDEID_ERROR);
                        obj.put(AppResultConstants.MSG, NotificationResultConstants.DEVICEDEID_ERROR_MSG);
                    }
                }
            }
        } catch (Exception e) {
            obj.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            obj.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public JSONObject pushMsgDeviceAll(NotificationParams no) {
        JSONObject obj = new JSONObject();
        try {
            logger.info("请求体:" + no);
            String title = no.getData().getAttributes().getTitle();
            String context = no.getData().getAttributes().getContext();
            String type = no.getData().getAttributes().getType();
            String vin = no.getData().getAttributes().getVin();
            String ids = UUID.randomUUID().toString().replaceAll("-", "");
            String imageURL = no.getData().getAttributes().getImageURL();
            String phoneNum = "999999";
            String token = "999999";
            String userId = "999999";

            if (title == null || "".equals(title)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.TITLE_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (context == null || "".equals(context)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.CONTENT_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (type == null || "".equals(type)) {
                obj.put(AppResultConstants.STATUS, NotificationResultConstants.TYPE_EMPTY);
                obj.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else {
                XingeApp xinge = new XingeApp(accessId, secretKey);
                MessageIOS mess = pushIOSClient(title, context, type, imageURL);
                //未读标识符，未读
                Integer readflag = 0;
                //设置时间格式
                String createDate = DateUtil.getDate("yyyy/MM/dd HH:mm:ss");
                String returnCode = xinge.pushAllDevice(0, mess, XingeApp.IOSENV_PROD).toString();
                //判断返回状态码是否推送成功
                if (returnCode.contains("ret_code")) {
                    //{"err_msg":"无效帐号，请检查后重试","ret_code":48}
                    JSONObject returnCodeJson = JSONObject.parseObject(returnCode);
                    String returnCodeString = String.valueOf(returnCodeJson.get("ret_code"));
                    if ("0".equals(returnCodeString)) {
                        obj.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                        obj.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);
                        //把推送消息插入mongodb
                        notificationMongoDBDao.insertAll(ids, phoneNum, token, title, vin, context, createDate, userId, type, readflag, imageURL);
                    } else {
                        obj.put(AppResultConstants.MSG, NotificationResultConstants.RETURN_CODE_ERROR);
                        obj.put(AppResultConstants.STATUS, NotificationResultConstants.RETURN_ERROR);
                        logger.info("返回状态码:" + returnCodeString);
                    }
                } else {
                    obj.put(AppResultConstants.STATUS, NotificationResultConstants.RESULT_ERROR);
                    obj.put(AppResultConstants.MSG, NotificationResultConstants.RETURN_CODE_ERROR);
                    logger.info("返回状态码:" + returnCode);
                }
            }
        } catch (Exception e) {
            obj.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            obj.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 封装信鸽IOS推送SDK
     */
    private MessageIOS pushIOSClient(String title, String context, String type, String imageURL) {
        JSONObject obj = new JSONObject();
        JSONObject aps = new JSONObject();
        JSONObject alert = new JSONObject();
        MessageIOS mess = new MessageIOS();
        mess.setExpireTime(86400);
        mess.setAlert("ios test");
        mess.setBadge(1);
        mess.setSound("beep.wav");
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("key", "value");
        mess.setCustom(custom);
        TimeInterval acceptTime = new TimeInterval(0, 0, 23, 59);
        mess.addAcceptTime(acceptTime);
        alert.put("title", title);
        alert.put("body", context);
        alert.put("type", type);
        aps.put("sound", "beep.wav");
        aps.put("alert", alert);
        aps.put("badge", 1);
        aps.put("content-available", 1);
        aps.put("mutable-content", 1);
        obj.put("xg_media_resources", imageURL);
        obj.put("aps", aps);
        mess.setRaw(obj.toString());
        return mess;
    }

    /**
     * 封装信鸽Android推送SDK
     */
    private Message pushAndroidClient(String title, String context, String type, String imageURL) {
        Message messAndroid = new Message();

        return messAndroid;

    }
}
