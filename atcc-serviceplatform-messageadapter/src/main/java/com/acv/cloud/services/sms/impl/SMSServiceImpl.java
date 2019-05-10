package com.acv.cloud.services.sms.impl;

import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.ApplicationPropertiesConstants;
import com.acv.cloud.frame.constants.app.NotificationResultConstants;
import com.acv.cloud.frame.util.DateUtil;
import com.acv.cloud.frame.util.SMSUtil;
import com.acv.cloud.repository.mongotemplate.ISMSDao;
import com.acv.cloud.services.sms.SMSService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liyang on 2018/12/24.
 */
@Service
class SMSServiceImpl implements SMSService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ApplicationPropertiesConstants applicationConstants;

    @Autowired
    private ISMSDao messagesDao;

    @Override
    public JSONObject sendSms(String phoneNum, String content) {
        JSONObject json = new JSONObject();

        //发送短信到目标手机号
        String uri = applicationConstants.getSmsUri();
        String account = applicationConstants.getSmsAccount();
        String pswd = applicationConstants.getSmsPassword();
        boolean needstatus = true;
        String product = "";
        String extno = "";

        //校验参数是否为空
        try {
            logger.info("请求手机号:" + phoneNum + ",短信内容:" + content);
            if (phoneNum == null || "".equals(phoneNum)) {
                json.put(AppResultConstants.STATUS, NotificationResultConstants.PHONE_EMPTY);
                json.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else if (content == null || "".equals(content)) {
                json.put(AppResultConstants.STATUS, NotificationResultConstants.SMS_CONTENT_EMPTY);
                json.put(AppResultConstants.MSG, NotificationResultConstants.PARAM_ERROR_MSG);
            } else {
                //String returnString = "20181225174804,0\n2361225174804155400\n";
                String returnString = SMSUtil.sendSms(uri, account, pswd, phoneNum, content, needstatus, product, extno);
                if (returnString.contains("\n") || returnString.contains("\r\n")) {
                    if (returnString.charAt(returnString.indexOf(",") + 1) == '0') {
                        //换行,且","后面为0代表发送成功
                        json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                        json.put(AppResultConstants.MSG, NotificationResultConstants.SUCCESS_MSG);

                        //将手机号和短信内容存到mongoDB中
                        String createDate = DateUtil.getDate("yyyy/MM/dd HH:mm:ss");
                        messagesDao.insertSms(phoneNum, content, createDate);
                    } else {
                        //发送失败,","后为错误代码
                        String errorcode = returnString.substring(returnString.indexOf(","), returnString.length());
                        json.put(AppResultConstants.MSG, NotificationResultConstants.RETURN_CODE_ERROR);
                        json.put(AppResultConstants.STATUS, NotificationResultConstants.RESULT_ERROR);
                        json.put("ErrorCode", Integer.parseInt(errorcode));
                        logger.info("ErrorCode" + Integer.parseInt(errorcode));
                    }
                } else {
                    json.put(AppResultConstants.MSG, NotificationResultConstants.RETURN_CODE_ERROR);
                    json.put(AppResultConstants.STATUS, NotificationResultConstants.RETURN_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("短信接口发送短信异常:" + e.getMessage());
            json.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            json.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
        }
        return json;
    }
}
