package com.acv.cloud.services.verification;

import com.acv.cloud.jsonBean.fegin.messageadapter.SMS;
import com.alibaba.fastjson.JSONObject;

/**
 * 验证码业务
 *
 * @author leo
 */
public interface VerificationCodeService {
    /***
     * 发送短信到目标电话
     * @param phoneNum       手机号
     * @param content        短信内容
     */
    JSONObject sendSmsToPhone(String phoneNum, String content);

    /**
     * @param phoneNum     手机号
     * @param SendVcode    手机发送的验证码
     * @return
     */
    JSONObject checkVerificationCode(String phoneNum, String SendVcode, String SessionVcode);

    /**
     * 根据验证码用途生成验证码存入缓存并发送sms到手机
     * @param phoneNum 手机号
     * @param vcodeType 验证码用途
     */
    JSONObject sendVcodeSms(String phoneNum , String vcodeType);

}
