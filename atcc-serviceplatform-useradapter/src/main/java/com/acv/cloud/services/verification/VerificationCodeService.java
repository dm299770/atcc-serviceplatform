package com.acv.cloud.services.verification;

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
     * @param SessionVcode session保存的验证码
     * @return
     */
    JSONObject checkVerificationCode(String phoneNum, String SendVcode, String SessionVcode);

}
