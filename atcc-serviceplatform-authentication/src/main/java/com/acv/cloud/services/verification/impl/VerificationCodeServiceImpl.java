package com.acv.cloud.services.verification.impl;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.ApplicationPropertiesConstants;
import com.acv.cloud.frame.util.SMSUtil;
import com.acv.cloud.services.verification.VerificationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.bind.util.JAXBSource;

/**
 * 验证码逻辑
 *
 * @author leo
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final static String SMS_ERROR = "短信发送失败";
    private final static String SMS_SUCCESS = "短信发送成功";
    private final static String SMS_RETURN_EX = "短信接口返回异常";
    private final static String SMS_VCODE_ERROR = "验证码格式不正确";
    private final static String SMS_VCODE_SUCCESS = "验证成功";
    private final static String SMS_VCODE_FAIL = "验证失败";

    //private final static String SMS_VCODE_INVALID = "验证码失效";

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplicationPropertiesConstants applicationConstants;

    @Override
    public JSONObject sendSmsToPhone(String phoneNum, String content) {
        JSONObject jsonObject = new JSONObject();
        //发送短信到目标手机号
        String uri = applicationConstants.getSmsUri();
        String account = applicationConstants.getSmsAccount();
        String pswd = applicationConstants.getSmsPassword();
        boolean needstatus = true;
        String product = "";
        String extno = "";


        try {
            String returnString = SMSUtil.sendSms(uri, account, pswd, phoneNum, content, needstatus, product, extno);
            //String returnString = "20110725160412,0\n" +
            // "1234567890100\n";
            if (returnString.contains("\n") || returnString.contains("\r\n")) {
                //换行,且","后面为0代表发送成功
                if (returnString.charAt(returnString.indexOf(",") + 1) == '0') {
                    jsonObject.put(AppResultConstants.MSG, SMS_SUCCESS);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                } else {
                    jsonObject.put(AppResultConstants.MSG, SMS_RETURN_EX);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                }

            } else {
                //发送失败,","后为错误代码

                String errorcode = returnString.substring(returnString.indexOf(","), returnString.length());
                jsonObject.put(AppResultConstants.MSG, SMS_RETURN_EX);
                jsonObject.put(AppResultConstants.STATUS, Integer.parseInt(errorcode));

            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("短信接口发送短信异常:" + e.getMessage());
            jsonObject.put(AppResultConstants.MSG, SMS_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);

        }


        return jsonObject;
    }

    @Override
    public JSONObject checkVerificationCode(String phoneNum, String SendVcode, String SessionVcode) {
        JSONObject jsonObject = new JSONObject();
        if (SendVcode == null && "".equals(SendVcode)) {//输入的验证码为空
            jsonObject.put(AppResultConstants.MSG, SMS_VCODE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else {
            if (SessionVcode == null && "".equals(SessionVcode)) {

                jsonObject.put(AppResultConstants.MSG, SMS_VCODE_FAIL);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
            } else {
                if (SendVcode.equals(SessionVcode)) {
                    jsonObject.put(AppResultConstants.MSG, SMS_VCODE_SUCCESS);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                } else {
                    jsonObject.put(AppResultConstants.MSG, SMS_VCODE_FAIL);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                }
            }

        }
        return jsonObject;
    }
}
