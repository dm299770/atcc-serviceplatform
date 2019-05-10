package com.acv.cloud.controller.verification;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.util.VcUtil;
import com.acv.cloud.services.verification.VerificationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author leo
 */
@RestController
@RequestMapping({"/verficationCode"})
public class VerificationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VerificationCodeService verificationCodeServices;

    /**
     * 手机验证码
     *
     * @param phoneNum
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/getVerificationCode/{phoneNum}")
//    public Object createVerificationCode(HttpServletResponse response, HttpServletRequest request, @PathVariable("phoneNum") String phoneNum) {
//        JSONObject jsonObject = new JSONObject();
//
//        //生成验证码并发送到目标手机
//        //相同手机号是否阻止一段时间?
//        String vcode = VcUtil.createRandomVcode();//生成6位随机验证码
//        // 存入会话session
//        HttpSession session = request.getSession();
//        session.setAttribute(phoneNum, vcode);
//        String content = "【日产中国】 您正在验证手机,验证码为" + vcode + ",请在5分钟内按提示提交验证码,切勿将验证码泄露于他人";
//        System.out.println("-------vcode:" + vcode);
//        try {
//            jsonObject = verificationCodeServices.sendSmsToPhone(phoneNum, content);
//            removeAttrbute(session, phoneNum);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("发送验证码异常:" + e.getMessage());
//            jsonObject.put(AppResultConstants.MSG, e.getMessage());
//            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
//        }
//        return jsonObject;
//    }

    /**
     * 设置1分钟后删除session中的验证码
     *
     * @param session
     * @param attrName
     */
    private void removeAttrbute(final HttpSession session, final String attrName) {
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // 删除session中存的验证码
//                session.removeAttribute(attrName);
//                timer.cancel();
//            }
//        }, 5 * 60 * 1000);

        ScheduledExecutorService scheduExec;
        scheduExec = Executors.newScheduledThreadPool(2);
        scheduExec.schedule(new Runnable() {
            public void run() {
                // 删除session中存的验证码
                session.removeAttribute(attrName);
                scheduExec.shutdown();
            }
        }, 5 * 1000 * 60, TimeUnit.MILLISECONDS);
    }

//    /**
//     * 提交验证码对比是否和短信中一致
//     *
//     * @param response
//     * @param request
//     * @param phoneNum 手机号
//     * @param vcode    验证码
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/checkVerificationCode/{phoneNum}/{vcode}")
//    public Object checkVerificationCode(HttpServletResponse response, HttpServletRequest request, @PathVariable("phoneNum") String phoneNum, @PathVariable("vcode") String vcode) {
//        JSONObject jsonObject = new JSONObject();
//        HttpSession session = request.getSession();
//        String sessionVcode = "";
//
//        try {
//            Object sessionVcodeObject = session.getAttribute(phoneNum);
//            if (sessionVcodeObject != null) {
//                sessionVcode = sessionVcodeObject.toString();
//            }
//            jsonObject = verificationCodeServices.checkVerificationCode(phoneNum, vcode, sessionVcode);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("验证码比对异常:" + e.getMessage());
//            jsonObject.put(AppResultConstants.MSG, e.getMessage());
//            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
//        }
//
//        return jsonObject;
//    }

//    public static void main(String args[]){
//        System.out.println("begin" );
//        ScheduledExecutorService scheduExec;
//        scheduExec =  Executors.newScheduledThreadPool(2);
//        scheduExec.schedule(new Runnable() {
//            public void run() {
//                System.out.println("success" );
//                scheduExec.shutdown();
//            }
//        },1000 * 5, TimeUnit.MILLISECONDS);
//
//    }


}

