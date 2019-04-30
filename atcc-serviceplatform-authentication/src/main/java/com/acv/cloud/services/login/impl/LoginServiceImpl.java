package com.acv.cloud.services.login.impl;

import com.acv.cloud.domain.body.resp.login.LoginData;
import com.acv.cloud.frame.constants.RedisConstants;
import com.acv.cloud.frame.constants.app.LoginAppResultConstants;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.OauthConstants;
import com.acv.cloud.frame.util.JwtTokenUtil;
import com.acv.cloud.mapper.user.TsUserMapper;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.repository.redistemplate.IUserDao;
import com.acv.cloud.services.login.LoginService;
import com.acv.cloud.services.user.impl.TsUserServiceImpl;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:登录接口实现
 * @author:@leo.
 */
@Service
public class LoginServiceImpl implements LoginService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TsUserMapper tsUserMapper;
    @Autowired
    private IUserDao userDao;

    @Override
    public JSONObject login(String phoneNum, String password, String deviceType, String deviceNo) {
        try {
            // 判断【用户名】、【密码】参数合法性
            JSONObject jsonObject = new JSONObject();

            //如果是手机设备
            if(containsType(deviceType)){
                if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
                    jsonObject.put(AppResultConstants.STATUS, LoginAppResultConstants.PHONENUM_EMPTY);
                    jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.PARAMER_ERROR_MSG);
                } else if (null == password || "".equalsIgnoreCase(password)) {
                    jsonObject.put(AppResultConstants.STATUS, LoginAppResultConstants.PASSWORD_EMPTY);
                    jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.PARAMER_ERROR_MSG);
                } else {
                    // 查找用户，判断用户账号密码是否正确
                    TsUser tsUser = tsUserMapper.findEffctiveByPhoneNum(phoneNum);
                    if (null == tsUser) {
                        jsonObject.put(AppResultConstants.STATUS, LoginAppResultConstants.USERINFO_EMPTY);
                        jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.USERINFO_EMPTY_MSG);
                        //} else if (!MD5Util.md5(password).equalsIgnoreCase(tsUser.getPassword())) {

                    } else if (!password.equalsIgnoreCase(tsUser.getPassword())) {
                        jsonObject.put(AppResultConstants.STATUS, LoginAppResultConstants.PASSWORD_ERROR);
                        jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.PARAMER_ERROR_MSG);
                    } else {
                        // 账号密码正确，生成token
                        //String accessToken = TokenUtils.createJwtToken(phoneNum);

                        long currentTime = System.currentTimeMillis() / 1000;
                        long accessToken_expireIn = currentTime + OauthConstants.ACCESS_TOKEN_EXPIREIN;
                        Map<String, Object> params = new HashMap<String, Object>();
                        //获取过期时间
                        params.put("EXPIREIN", accessToken_expireIn);
                        params.put("SCOPE", null);
                        params.put("APP_ACCOUNT_ID", tsUser.getUserId());
                        params.put(RedisConstants.LOGIN_DEVICENO,deviceNo);
                        params.put(RedisConstants.LOGIN_DEVICETYPE,deviceType);
                        //生成accessToken & refreshToken
                        String accessToken = JwtTokenUtil.generateToken(params);
                        String refreshToken = JwtTokenUtil.generateRefreshToken();

                        //设备绑定唯一用户
                        //单设备登陆暂未实现
                        userDao.updateDeviceNo(tsUser.getUserId(), deviceType ,deviceNo);


                        LoginData data = new LoginData(accessToken,refreshToken);
                        jsonObject.put(AppResultConstants.DATA, data);
                        jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                        jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.LOGIN_SUCCESS_MSG);
                    }
                }

            }else{
                jsonObject.put(AppResultConstants.STATUS, LoginAppResultConstants.LOGIN_DEVICETYPE_ERROR);
                jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.PARAMER_ERROR_MSG);
            }



            return jsonObject;
        } catch (Exception e) {
            return excException(e);

        }
    }

    private JSONObject excException(Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        JSONObject jsonExcptionObject = new JSONObject();
        jsonExcptionObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
        jsonExcptionObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        return jsonExcptionObject;
    }

//    @Override
//    public JSONObject login(String phoneNum, String password) {
//        try {
//            // 判断【用户名】、【密码】参数合法性
//            JSONObject jsonObject = new JSONObject();
//
//            if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
//                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
//                jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.USER_NAME_ERROR);
//            } else if (null == password || "".equalsIgnoreCase(password)) {
//                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
//                jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.PASSWORD_ERROR);
//            } else {
//                // 查找用户，判断用户账号密码是否正确
//                TsUser tsUser = tsUserMapper.findEffctiveByPhoneNum(phoneNum);
//                if (null == tsUser) {
//                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
//                    jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.USER_ERROR);
//                    //} else if (!MD5Util.md5(password).equalsIgnoreCase(tsUser.getPassword())) {
//                } else if (!password.equalsIgnoreCase(tsUser.getPassword())) {
//                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
//                    jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.PASSWORD_WRONG_ERROR);
//                } else {
//                    // 账号密码正确，生成token
//                    //String accessToken = TokenUtils.createJwtToken(phoneNum);
//
//                    long currentTime = System.currentTimeMillis() / 1000;
//                    long accessToken_expireIn = currentTime + OauthConstants.ACCESS_TOKEN_EXPIREIN;
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    //获取过期时间
//                    params.put("EXPIREIN", accessToken_expireIn);
//                    params.put("SCOPE", null);
//                    params.put("APP_ACCOUNT_ID", phoneNum);
//                    //生成access Token
//                    String accessToken = JwtTokenUtil.generateToken(params);
//
//                    //将设备号和号码绑定
//                    //userDao.updateDeviceNo(phoneNum,deviceNo);
//                    LoginData data = new LoginData(accessToken);
//                    jsonObject.put(AppResultConstants.DATA, data);
//                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
//                    jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.LOGIN_SUCCESS);
//                }
//            }
//
//            return jsonObject;
//        } catch (Exception e) {
//            return excException(e);
//
//        }
//    }

    @Override
    public JSONObject logout(String phoneNum) {
        JSONObject jsonObject = new JSONObject();

        if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
            jsonObject.put(AppResultConstants.MSG, LoginAppResultConstants.PARAMER_ERROR_MSG);
        } else {
            // 查找用户，判断用户账号密码是否正确
            TsUser tsUser = tsUserMapper.findEffctiveByPhoneNum(phoneNum);
            if (null == tsUser) {
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.USER_ERROR);
            } else {
                try {
                    userDao.deleteDeviceNo(phoneNum);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                    jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.LOGOUT_SUCCESS);
                } catch (Exception e) {
                    logger.error("logout error ! --" + e.getMessage());
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                    jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.LOGOUT_ERROR);
                }

            }

        }

        return jsonObject;
    }


    private static boolean containsType(String type) {

        for (RedisConstants.ALL_DEVICETYPES devicetypes : RedisConstants.ALL_DEVICETYPES.values()) {
            if (devicetypes.name().equals(type)) {
                return true;
            }
        }

        return false;
    }
}
