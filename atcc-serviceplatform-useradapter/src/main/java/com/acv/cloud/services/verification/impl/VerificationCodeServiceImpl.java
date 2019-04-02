package com.acv.cloud.services.verification.impl;

import com.acv.cloud.fegin.messageadapter.ImessageFegin;
import com.acv.cloud.frame.constants.DataFormat;
import com.acv.cloud.frame.constants.RedisConstants;
import com.acv.cloud.frame.util.DateFormatUtil;
import com.acv.cloud.frame.util.VcUtil;
import com.acv.cloud.jsonBean.fegin.messageadapter.SMS;
import com.acv.cloud.jsonBean.fegin.messageadapter.sms.Attributes;
import com.acv.cloud.jsonBean.fegin.messageadapter.sms.Data;
import com.acv.cloud.jsonBean.fegin.messageadapter.sms.SMSParams;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.user.TsUserService;
import com.acv.cloud.services.user.impl.TsUserServiceImpl;
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
import java.util.Date;

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
    public final static String VEFITYCODE_SUCCESS = "验证码发送成功";
    public final static String VEFITYCODE_FAIL = "验证码发送失败";
    //private final static String SMS_VCODE_INVALID = "验证码失效";

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplicationPropertiesConstants applicationConstants;

    @Autowired
    TsUserService tsUserService;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private ImessageFegin imessageFegin;

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

    @Override
    public JSONObject sendVcodeSms(String phoneNum, String vcodeType) {
        JSONObject jsonObject = new JSONObject();
        //生成验证码并发送到目标手机
        if(vcodeType!=null&&!"".equals(vcodeType)){
            //跟据验证码类型做相应验证
            if(RedisConstants.REGISTER_HEAD.equals(vcodeType)){
                //注册验证码验证手机号是否已注册
                if (tsUserService.findEffctiveByPhoneNum(phoneNum) != null) {
                    jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.CELL_EXIST_ERROR);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                    return jsonObject;
                }
            }
        }

        if(phoneNum == null || "".equals(phoneNum)){
            jsonObject.put(AppResultConstants.MSG,AppResultConstants.PARAMER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }
        String vcode = null ;

        String vcodeCacheKey = String.format(vcodeType+":%s",phoneNum);//生成缓存验证码key
        if(redisRepository.get(vcodeCacheKey)!=null){
            //缓存中存在已发送的验证码,则发送缓存中验证码
            vcode = redisRepository.get(vcodeCacheKey).toString();
        }else{
            //缓存中不存在验证码
            vcode =  VcUtil.createRandomVcode();//生成6位随机验证码
            //将验证码根据用途存入缓存,有效期5分钟
            redisRepository.set(vcodeCacheKey,vcode,300l);
        }

        if(vcode!=null){

            String content = "【日产中国】 短信验证码为" + vcode + ",请在5分钟内按提示提交验证码,切勿将验证码泄露于他人";
            logger.info("VerificationCodeServiceImpl: 生成验证码为: "+vcode);
            try {
                //SMS sms = new SMS(phoneNum,content, DateFormatUtil.getTimesecond());
                //2019.03.29修改短信推送协议
                Attributes attributes = new Attributes();
                Data data = new Data();
                SMSParams sms = new SMSParams();
                attributes.setContent(content);
                attributes.setPhoneNum(phoneNum);
                data.setAttributes(attributes);
                sms.setData(data);
                //attributes.setCreateDate(new Date());

                Object messageJsonObject = imessageFegin.sendSmsToPhone(sms);
                if (messageJsonObject == null){
                    //远程调用短信服务失败
                    logger.debug("VerificationCodeServiceImpl:远程调用短信服务失败");

                }
                JSONObject smsJson = JSONObject.parseObject(messageJsonObject.toString());
                if(smsJson!=null){
                    if((Integer) smsJson.get(AppResultConstants.STATUS)==200){
                        jsonObject.put(AppResultConstants.MSG, VEFITYCODE_SUCCESS);
                        jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
                    }
                    jsonObject = smsJson;
                }

                //jsonObject = verificationCodeServices.sendSmsToPhone(phoneNum, content);
                //removeAttrbute(session, phoneNum);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("发送验证码异常:" + e.getMessage());
                jsonObject.put(AppResultConstants.MSG, e.getMessage());
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }
        }else{
            logger.info("VerificationCodeServiceImpl phoneNum:["+phoneNum+"] 验证码生成失败");
            jsonObject.put(AppResultConstants.MSG, VEFITYCODE_FAIL);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }
        return jsonObject;
    }
}
