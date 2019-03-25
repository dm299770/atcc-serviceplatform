package com.acv.cloud.controller.user;


import com.acv.cloud.jsonBean.user.changePassword.requestJson.ChangePasswordParams;
import com.acv.cloud.jsonBean.user.forgotPassword.requestJson.ForgetPasswordParams;
import com.acv.cloud.jsonBean.user.verifyCode.requestJson.Attributes;
import com.acv.cloud.jsonBean.user.verifyCode.requestJson.Data;

import com.acv.cloud.jsonBean.user.create.requestJson.CreateParams;
import com.acv.cloud.jsonBean.user.verifyCode.requestJson.VerifyCodeParams;

import com.acv.cloud.services.verification.VerificationCodeService;
import com.alibaba.fastjson.JSONObject;

import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.util.FileUtil;
import com.acv.cloud.frame.util.JsonUtil;

import com.acv.cloud.jsonBean.user.UserInfoRequsetBody;

import com.acv.cloud.services.user.TsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息
 *
 * @author leo
 */
@RestController
@RequestMapping({"/user"})
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * App 反馈提示信息
     */
    public final static String Ex_ERROR = "文件类型错误";
    public final static String SELECT_SUCCESS = "获取成功";
    public final static String IMG_ERROR = "图片尺寸过大";



    @Autowired
    private TsUserService tsUserServices;

    @Autowired
    private VerificationCodeService verificationCodeService;




    /**
     * 获取用户信息
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/getInfo/v1")
    public Object getUserInfo(@CurrentUser UserInfo user) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("msg", SELECT_SUCCESS);
            jsonObject.put("status", AppResultConstants.SUCCESS_STATUS);
            jsonObject.put("data", user);

            jsonObject = JsonUtil.EraseNull(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户信息异常");
            jsonObject.put("msg", "服务器异常");
            jsonObject.put("status", AppResultConstants.ERROR_STATUS);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public UserInfo getUser(@PathVariable("id")String uuid) {
        UserInfo userInfo = null ;
        try {
             userInfo = tsUserServices.findEffctiveUserInfoById(uuid);
        } catch (Exception e) {
            logger.error("getUser: " + e);
            e.printStackTrace();
        }
        return userInfo;
    }


    /**
     * 注册用户
     **/
    @ResponseBody
    //@RequestMapping(value = "/registeredUser/{phoneNum}/{password}")
    @RequestMapping(value = "/create/v1")
    public Object registeredUser(@RequestBody CreateParams createParams) {

        logger.info("UserController createParams:" +createParams.toString());
        //手机号
        String phoneNum = Optional.ofNullable(createParams)
                .map(CreateParams::getData)
                .map(com.acv.cloud.jsonBean.user.create.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.create.requestJson.Attributes::getPhoneNumber).orElse(null);
        //密码
        String password = Optional.ofNullable(createParams)
                .map(CreateParams::getData)
                .map(com.acv.cloud.jsonBean.user.create.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.create.requestJson.Attributes::getPassword).orElse(null);
        //验证码
        String code = Optional.ofNullable(createParams)
                .map(CreateParams::getData)
                .map(com.acv.cloud.jsonBean.user.create.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.create.requestJson.Attributes::getCode).orElse(null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = tsUserServices.registeredUser(phoneNum, password, code);
        } catch (Exception e) {
            logger.error("registeredUser:" + e);
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }
        return jsonObject;

    }

    /***
     *重置密码
     **/
    //@LoginRequired (重置密码不需验证用户jwt)
    @ResponseBody
    @RequestMapping(value = "/resetPassword/v1")
    public Object resetUserPassword(@RequestBody ForgetPasswordParams forgetPasswordParams) {
        //JSONObject jsonObject = sysUserServices.resetUserPassword(userId, newPassword);
        logger.info("UserController forgetPasswordParams:" +forgetPasswordParams.toString());
        //手机号
        String phoneNum = Optional.ofNullable(forgetPasswordParams)
                .map(ForgetPasswordParams::getData)
                .map(com.acv.cloud.jsonBean.user.forgotPassword.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.forgotPassword.requestJson.Attributes::getPhoneNum).orElse(null);
        String newPassword = Optional.ofNullable(forgetPasswordParams)
                .map(ForgetPasswordParams::getData)
                .map(com.acv.cloud.jsonBean.user.forgotPassword.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.forgotPassword.requestJson.Attributes::getNewPassword).orElse(null);
        String code = Optional.ofNullable(forgetPasswordParams)
                .map(ForgetPasswordParams::getData)
                .map(com.acv.cloud.jsonBean.user.forgotPassword.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.forgotPassword.requestJson.Attributes::getCode).orElse(null);

        JSONObject jsonObject = tsUserServices.resetUserPassword(phoneNum, newPassword, code);
        return jsonObject;
    }

    /**
     * 修改密码
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/changePassword/v1")
    public Object modifyUserPassword(@CurrentUser UserInfo user, @RequestBody  ChangePasswordParams changePasswordParams) {
        logger.info("UserController changePasswordParams:"+changePasswordParams.toString());

        //旧密码
        String oldPassword = Optional.ofNullable(changePasswordParams)
                .map(ChangePasswordParams::getData)
                .map(com.acv.cloud.jsonBean.user.changePassword.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.changePassword.requestJson.Attributes::getOldPassword).orElse(null);
        //新密码
        String newPassword = Optional.ofNullable(changePasswordParams)
                .map(ChangePasswordParams::getData)
                .map(com.acv.cloud.jsonBean.user.changePassword.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.changePassword.requestJson.Attributes::getNewPassword).orElse(null);


        String userId = user.getUserId();
        JSONObject jsonObject = tsUserServices.modifyUserPassword(userId, oldPassword, newPassword);
        return jsonObject;
    }

    /**
     * 修改用户属性(单一)
     *
     * @param user
     * @param userInfoRequsetBody
     * @return
     */
    @LoginRequired
    @ResponseBody
    //@RequestMapping(value = "/modifyUserInfo/{type}/{value}")
    @RequestMapping(value = "/changeInfo/v1")
    public Object modifyUserInfo(@CurrentUser UserInfo user, @RequestBody UserInfoRequsetBody userInfoRequsetBody) {
        logger.info("UserController changeInfo:"+userInfoRequsetBody.toString());
        JSONObject jsonObject = null;
        String type = userInfoRequsetBody.getType();
        String value = userInfoRequsetBody.getValue();

        if (user == null) {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.LOGIN_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else {
            logger.info(user.getPhoneNum());
            logger.info("registeredUser:phoneNum:" + user.getPhoneNum() + ",type:" + type + ",value:" + value);
            jsonObject = tsUserServices.modifyUserInfo(user.getUserId(), type, value);
        }
        return jsonObject;
    }

    /**
     * @param user
     * @param imageFile 头像文件
     * @return
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/uploadAvator/v1")
    public Object uploadProfilePhoto(@CurrentUser UserInfo user, @RequestParam(value = "imageFile") MultipartFile imageFile) {
        //System.out.println(imageFile.getSize()) ;

        logger.info("UserController uploadProfilePhoto:["+imageFile.getName()+"]");
        long size = imageFile.getSize();
        JSONObject jsonObject = new JSONObject();
        String[] exArray = {"png", "jpg", "gif"};//属性值必须包含于这4项
        List<String> typeList = Arrays.asList(exArray);
        //判断拓展名
        if (!typeList.contains(FileUtil.getTypePart(imageFile.getOriginalFilename()))) {
            jsonObject.put(AppResultConstants.MSG, Ex_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
            return jsonObject;
        }
        if(size>0 && size > 1024*1024 ){
            jsonObject.put(AppResultConstants.MSG, IMG_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
            return jsonObject;
        }
        jsonObject = tsUserServices.setProfilePhoto(user.getUserId(), user.getUserId(), imageFile);

        return jsonObject;
    }


    /**
     * 手机验证码
     *
     * @param verifyCodeParams
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCode/v1")
    public Object createVerificationCode(@RequestBody VerifyCodeParams verifyCodeParams) {
        logger.info("UserController verifyCodeParams:"+verifyCodeParams.toString());
        String phoneNum = Optional.ofNullable(verifyCodeParams)
                .map(VerifyCodeParams::getData)
                .map(Data::getAttributes)
                .map(Attributes::getPhoneNum).orElse(null);
        String type = Optional.ofNullable(verifyCodeParams)
                .map(VerifyCodeParams::getData)
                .map(Data::getAttributes)
                .map(Attributes::getType).orElse(null);

        JSONObject jsonObject = verificationCodeService.sendVcodeSms(phoneNum,type);

        return jsonObject;
    }
}


