package com.acv.cloud.controller.user;

import com.acv.cloud.domain.body.req.userInfo.changePassword.ChangePasswordParams;
import com.acv.cloud.domain.body.req.userInfo.create.CreateParams;
import com.acv.cloud.domain.body.req.userInfo.forgetPassword.ForgetPasswordParams;
import com.acv.cloud.domain.body.req.verifycode.getCode.VerifyCodeParams;
import com.acv.cloud.domain.dto.UserInfo;
import com.acv.cloud.domain.body.req.userInfo.changeInfo.ChangInfoReqBody;
import com.acv.cloud.frame.constants.app.UserAppResultConstants;
import com.acv.cloud.services.verification.VerificationCodeService;
import com.alibaba.fastjson.JSONObject;

import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.util.FileUtil;
import com.acv.cloud.frame.util.JsonUtil;

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
    @RequestMapping(value = "/getInfo/{version}")
    public Object getUserInfo(@CurrentUser UserInfo user,@PathVariable String verison) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(AppResultConstants.MSG , UserAppResultConstants.GET_DATA_SUCCESS_MSG);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
            jsonObject.put(AppResultConstants.DATA, user);

            jsonObject = JsonUtil.EraseNull(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户信息异常");
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
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
    @RequestMapping(value = "/create/{version}")
    public Object registeredUser(@RequestBody CreateParams createParams, @PathVariable String version) {

        logger.info("UserController createParams:" +createParams.toString());
        //手机号
        String phoneNum = Optional.ofNullable(createParams)
                .map(CreateParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.create.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.create.Attributes::getPhoneNumber).orElse(null);
        //密码
        String password = Optional.ofNullable(createParams)
                .map(CreateParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.create.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.create.Attributes::getPassword).orElse(null);
        //验证码
        String code = Optional.ofNullable(createParams)
                .map(CreateParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.create.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.create.Attributes::getCode).orElse(null);

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
    @RequestMapping(value = "/resetPassword/{version}")
    public Object resetUserPassword(@RequestBody ForgetPasswordParams forgetPasswordParams , @PathVariable String version) {
        //JSONObject jsonObject = sysUserServices.resetUserPassword(userId, newPassword);
        logger.info("UserController forgetPasswordParams:" +forgetPasswordParams.toString());
        //手机号
        String phoneNum = Optional.ofNullable(forgetPasswordParams)
                .map(ForgetPasswordParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.forgetPassword.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.forgetPassword.Attributes::getPhoneNum).orElse(null);
        String newPassword = Optional.ofNullable(forgetPasswordParams)
                .map(ForgetPasswordParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.forgetPassword.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.forgetPassword.Attributes::getNewPassword).orElse(null);
        String code = Optional.ofNullable(forgetPasswordParams)
                .map(ForgetPasswordParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.forgetPassword.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.forgetPassword.Attributes::getCode).orElse(null);

        JSONObject jsonObject = tsUserServices.resetUserPassword(phoneNum, newPassword, code);
        return jsonObject;
    }

    /**
     * 修改密码
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/changePassword/{version}")
    public Object modifyUserPassword(@CurrentUser UserInfo user, @RequestBody ChangePasswordParams changePasswordParams, @PathVariable String version) {
        logger.info("UserController changePasswordParams:"+changePasswordParams.toString());

        //旧密码
        String oldPassword = Optional.ofNullable(changePasswordParams)
                .map(ChangePasswordParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.changePassword.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.changePassword.Attributes::getOldPassword).orElse(null);
        //新密码
        String newPassword = Optional.ofNullable(changePasswordParams)
                .map(ChangePasswordParams::getData)
                .map(com.acv.cloud.domain.body.req.userInfo.changePassword.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.userInfo.changePassword.Attributes::getNewPassword).orElse(null);


        String userId = user.getUserId();
        JSONObject jsonObject = tsUserServices.modifyUserPassword(userId, oldPassword, newPassword);
        return jsonObject;
    }

    /**
     * 修改用户属性(单一)
     *
     * @param user
     * @param changInfoReqBody
     * @return
     */
    @LoginRequired
    @ResponseBody
    //@RequestMapping(value = "/modifyUserInfo/{type}/{value}")
    @RequestMapping(value = "/changeInfo/{version}")
    public Object modifyUserInfo(@CurrentUser UserInfo user, @RequestBody ChangInfoReqBody changInfoReqBody, @PathVariable String version) {
        logger.info("UserController ChangInfoReqBody:"+changInfoReqBody.toString());
        JSONObject jsonObject = null;

        if (user == null) {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.LOGIN_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.AUTHENTICATION_FAILURE);
        } else {
            logger.info(" UserController modifyUserInfo [phoneNum] :"+user.getPhoneNum());
            jsonObject = tsUserServices.modifyUserInfo(user.getUserId(), changInfoReqBody);
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
    @RequestMapping(value = "/uploadAvator/{version}")
    public Object uploadProfilePhoto(@CurrentUser UserInfo user, @RequestParam(value = "imageFile") MultipartFile imageFile,@PathVariable String version) {
        //System.out.println(imageFile.getSize()) ;

        logger.info("UserController uploadProfilePhoto:["+imageFile.getName()+"]");
        long size = imageFile.getSize();
        JSONObject jsonObject = new JSONObject();
        String[] exArray = {"png", "jpg", "gif"};//属性值必须包含于这4项
        List<String> typeList = Arrays.asList(exArray);
        //判断拓展名
        if (!typeList.contains(FileUtil.getTypePart(imageFile.getOriginalFilename()))) {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.PARAM_ERROR_MSG);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.PARAM_ERROR);
            return jsonObject;
        }
        if(size>0 && size > 1024*1024 ){
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.PARAM_ERROR_MSG);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.PARAM_ERROR);
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
    @RequestMapping(value = "/getCode/{version}")
    public Object createVerificationCode(@RequestBody VerifyCodeParams verifyCodeParams, @PathVariable String version) {
        logger.info("UserController verifyCodeParams:"+verifyCodeParams.toString());
        String phoneNum = Optional.ofNullable(verifyCodeParams)
                .map(VerifyCodeParams::getData)
                .map(com.acv.cloud.domain.body.req.verifycode.getCode.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.verifycode.getCode.Attributes::getPhoneNum).orElse(null);
        String type = Optional.ofNullable(verifyCodeParams)
                .map(VerifyCodeParams::getData)
                .map(com.acv.cloud.domain.body.req.verifycode.getCode.Data::getAttributes)
                .map(com.acv.cloud.domain.body.req.verifycode.getCode.Attributes::getType).orElse(null);

        JSONObject jsonObject = verificationCodeService.sendVcodeSms(phoneNum,type);

        return jsonObject;
    }
}


