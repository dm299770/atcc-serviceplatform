package com.acv.cloud.frame;

import com.bcloud.msg.http.HttpSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送短信工具类
 */
public class SMSUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *向单个手机号发送短信
     *
     * @param uri                    应用地址
     * @param account                账号
     * @param pswd                   密码
     * @param mobiles                手机号码
     * @param content                短信内容
     * @param needstatus             是否需要状态报告，需要true，不需要false
     * @param product                产品ID
     * @param extno                  扩展码
     * @return
     */
    public static String sendSms(String uri,String account,String pswd ,String mobiles,String content,boolean needstatus,String product,String extno) throws Exception {
        String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno);
        return returnString;
    }

    /**
     *向单个手机号发送短信
     *
     * @param uri                    应用地址
     * @param account                账号
     * @param pswd                   密码
     * @param mobiles                手机号码,多个号码使用","分割
     * @param content                短信内容
     * @param needstatus             是否需要状态报告，需要true，不需要false
     * @param product                产品ID
     * @param extno                  扩展码
     * @return
     */
    public static String batchSendSms(String uri,String account,String pswd ,String mobiles,String content,boolean needstatus,String product,String extno) throws Exception {
        String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno);
        return returnString;
    }

}
