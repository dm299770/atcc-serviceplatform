package com.acv.cloud.services.user.impl;

import com.acv.cloud.frame.constants.RedisConstants;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.ApplicationPropertiesConstants;
import com.acv.cloud.frame.util.FileUtil;
import com.acv.cloud.frame.util.MD5Util;
import com.acv.cloud.frame.util.SMSUtil;
import com.acv.cloud.frame.util.VcUtil;
import com.acv.cloud.mapper.user.TsUserInfoMapper;
import com.acv.cloud.mapper.user.TsUserMapper;
import com.acv.cloud.models.sys.TmChargeAccount;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.models.sys.TsUserInfo;
import com.acv.cloud.services.user.TsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Registration;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * 系统用户service
 *
 * @author leo
 */
@Service
public class TsUserServiceImpl implements TsUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 返回给APP端提示信息
     */
    public final static String CELL_PHONE_ERROR = "手机号格式不正确";
    public final static String CELL_EXIST_ERROR = "手机号已注册";
    public final static String CELL_NOTEXIST_ERROR = "手机号未注册或已失效";
    public final static String PASSWORD_ERROR = "密码格式不正确";
    public final static String CODE_EMPTY = "验证码不能为空";
    public final static String CODE_ERROR = "验证码不正确";
    public final static String PASSWORD_WRONG_ERROR = "密码错误";
    public final static String OLD_PASSWORD_ERROR = "原密码格式不正确";
    public final static String OLD_PASSWORD_WRONG_ERROR = "原密码不正确";
    public final static String NEW_PASSWORD_ERROR = "新密码格式不正确";
    public final static String TYPE_ERROR = "属性值不存在或不能为空";
    public final static String SAVE_SUCCESS = "保存成功";
    public final static String SIGNIN_SUCCESS = "注册成功";
    public final static String LOGIN_SUCCESS = "登录成功";
    public final static String LOGOUT_SUCCESS = "注销成功";
    public final static String LOGOUT_ERROR = "注销失败";
    public final static String MODIFY_SUCCESS = "修改成功";
    public final static String SERVER_ERROR = "服务器异常";
    public final static String MODIFY_FAIL = "修改失败";

    public final static String USER_ERROR = "用户信息错误";
    public final static String USER_NAME_ERROR = "账号格式不正确";


    @Autowired
    private TsUserMapper tsUserMapper;
    @Autowired
    private TsUserInfoMapper tsUserInfoMapper;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private ApplicationPropertiesConstants applicationConstants;

    private final String[] typeArray = {"NICK_NAME", "REAL_NAME", "SEXUAL", "EMEY_CONTACT", "PROFILE_PHOTO","PHONE_NUM"};//属性值必须包含于这4项


    @Override
    public TsUser findById(String id) {
        return tsUserMapper.findById(id);
    }

    @Override
    public UserInfo findEffctiveUserInfoByPhoneNum(String phoneNum) {
        return tsUserMapper.findEffctiveUserInfoByPhoneNum(phoneNum);
    }

    @Override
    public UserInfo findEffctiveUserInfoById(String uuid){
        return tsUserMapper.findEffctiveUserInfoById(uuid);
    }

    @Override
    public TsUser findEffctiveByPhoneNum(String phoneNum) {
        return tsUserMapper.findEffctiveByPhoneNum(phoneNum);
    }

    @Override
    public void save(TsUser bean) {
        tsUserMapper.save(bean);
    }

    @Override
    public void saveUserInfo(TsUserInfo bean) {
        tsUserInfoMapper.save(bean);
    }

    @Override
    public void saveUserAccount(TmChargeAccount bean) {
        tsUserInfoMapper.saveAccount(bean);
    }

    @Override
    public void delete(String[] userIds) {
        tsUserMapper.delete(userIds);
    }


    @Override
    public void updateUserInfo(String userId, String type, String value) {
        tsUserInfoMapper.updateByType(userId, type, value);
    }

    @Override
    public void updatePassword(String userId, String oldPassword, String newPassword) {
        tsUserMapper.updatePassword(userId, oldPassword, newPassword);
    }

    @Override
    public void reSetPassword(String phoneNum, String newPassword) {
        tsUserMapper.reSetPassword(phoneNum, newPassword);
    }

    @Transactional(value = "txManager")
    @Override
    public JSONObject registeredUser(String phoneNum, String password, String token) throws RuntimeException {
        //验证码校验,功能暂未实现

        JSONObject jsonObject = new JSONObject();
        // 1.校验参数合法性(phoneNum、password)
        if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
            //手机号不能为空
            jsonObject.put(AppResultConstants.MSG, CELL_PHONE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else if (null == password || "".equalsIgnoreCase(password)) {
            //密码不能为空
            jsonObject.put(AppResultConstants.MSG, PASSWORD_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }else if(null == token || "".equals(token) ){
            //验证码不能为空
            jsonObject.put(AppResultConstants.MSG, CODE_EMPTY);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }else if(!verifyCode(phoneNum,token,RedisConstants.REGISTER_HEAD)){
            //验证验证码是否正确
            jsonObject.put(AppResultConstants.MSG, CODE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }
        //注册用户是否存在验证
        else if (findEffctiveByPhoneNum(phoneNum) != null) {
            jsonObject.put(AppResultConstants.MSG, CELL_EXIST_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else {

            //验证码成功
            TsUser user = new TsUser();
            String uuid = UUID.randomUUID() + "";
            user.setUserId(uuid);
            user.setPhoneNum(phoneNum);
            user.setPassword(password);
            user.setIsEffctive(1);
            user.setCreateDate(new Date());
            user.setToken(null);
            //添加userInfo信息
            TsUserInfo userInfo = new TsUserInfo();
            userInfo.setUserId(uuid);
            userInfo.setNickName("user_" + (Math.random() * 9 + 1) * 1000);//默认初始昵称
            userInfo.setIsEffctive(1);
            userInfo.setCreateDate(new Date());
            //添加userAccount
            TmChargeAccount userAccount = new TmChargeAccount();
            userAccount.setUserAccount(uuid);
            userAccount.setBalance("0");
            userAccount.setCreateTime(new Date());

            tsUserMapper.save(user);
            tsUserInfoMapper.save(userInfo);
            tsUserInfoMapper.saveAccount(userAccount);
            // 3.组织返回信息
            jsonObject.put(AppResultConstants.MSG, SIGNIN_SUCCESS);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);

        }
        return jsonObject;
    }

    @Override
    public JSONObject resetUserPassword(String phoneNum, String newPassword,String code) {
        JSONObject jsonObject = new JSONObject();
        // 校验参数合法性
        if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
            jsonObject.put(AppResultConstants.MSG, CELL_PHONE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else if (null == newPassword || "".equalsIgnoreCase(newPassword)) {
            jsonObject.put(AppResultConstants.MSG, NEW_PASSWORD_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }
        //注册用户是否存在验证
        else if (findEffctiveByPhoneNum(phoneNum) == null) {
            jsonObject.put(AppResultConstants.MSG, CELL_NOTEXIST_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }
        else if(!verifyCode(phoneNum,code,RedisConstants.FORGOTPASSWORD_HEAD)){
            //验证验证码是否正确
            jsonObject.put(AppResultConstants.MSG, CODE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        }else {
            try {
                reSetPassword(phoneNum, newPassword);
                jsonObject.put(AppResultConstants.MSG, MODIFY_SUCCESS);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
            } catch (Exception e) {
                logger.error("resetUserPassword:" + e);
                jsonObject.put(AppResultConstants.MSG, SERVER_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);


            }
        }
        return jsonObject;
    }


    @Override
    public JSONObject modifyUserPassword(String userId, String oldPassword, String newPassword) {
        JSONObject jsonObject = new JSONObject();
        // 校验参数合法性
        if (null == oldPassword || "".equalsIgnoreCase(oldPassword)) {
            jsonObject.put(AppResultConstants.MSG, OLD_PASSWORD_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else if (null == newPassword || "".equalsIgnoreCase(newPassword)) {
            jsonObject.put(AppResultConstants.MSG, NEW_PASSWORD_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else {
            try {
                //TsUser tsUser = findEffctiveByPhoneNum(phoneNum);
                TsUser tsUser = findById(userId);
                if (null != tsUser && oldPassword.equalsIgnoreCase(tsUser.getPassword())) {
                    updatePassword(userId, oldPassword, newPassword);
                    jsonObject.put(AppResultConstants.MSG, MODIFY_SUCCESS);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                } else {
                    jsonObject.put(AppResultConstants.MSG, OLD_PASSWORD_WRONG_ERROR);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                }

            } catch (Exception e) {
                logger.error("modifyUserPassword:" + e);
                jsonObject.put(AppResultConstants.MSG, SERVER_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject modifyUserInfo(String userId, String type, String value) {
        JSONObject jsonObject = new JSONObject();

        List<String> typeList = Arrays.asList(typeArray);
        // 1.校验参数合法性
       /* if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
            jsonObject.put(AppResultConstants.MSG, CELL_PHONE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else*/ if (null == type || "".equalsIgnoreCase(type)) {
            jsonObject.put(AppResultConstants.MSG, TYPE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else if (!typeList.contains(type)) {
            jsonObject.put(AppResultConstants.MSG, TYPE_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else {
            // 更新用户信息
            try {
                updateUserInfo(userId, type, value);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                jsonObject.put(AppResultConstants.MSG, MODIFY_SUCCESS);
            } catch (Exception e) {
                logger.error("modifyUserInfo:" + e);
                jsonObject.put(AppResultConstants.MSG, SERVER_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject setProfilePhoto(String userid, String phoneNum, MultipartFile file) {

        JSONObject jsonObject = new JSONObject();
        //将头像输出到指定目录
        String photopath = applicationConstants.getPhotoPath();
        if (photopath == null && photopath.equalsIgnoreCase("")) {
            logger.debug("头像文件目标路径不存在");
            jsonObject.put(AppResultConstants.MSG, SERVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);

        } else if (file == null) {
            //验证文件不能为空
            jsonObject.put(AppResultConstants.MSG, MODIFY_FAIL);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
        } else {

            try {
                // File targetPhoto = new File(photopath+File.separator+userid+File.separator+System.currentTimeMillis()+".png");
                //File phontoParentPath = new File(photopath+File.separator+userid);
               /* if(!targetPhoto.getParentFile().exists()){
                    targetPhoto.getParentFile().mkdirs();
                }

                if(!targetPhoto.exists()){
                    targetPhoto.createNewFile();
                }*/

                File fileRoot = new File(photopath + File.separator + userid);
                String newFileName = System.currentTimeMillis() + "." + FileUtil.getTypePart(file.getOriginalFilename());
                if (!fileRoot.exists()) {
                    fileRoot.mkdirs();
                }
                File targetFile = new File(fileRoot.getAbsolutePath(), newFileName);
                file.transferTo(targetFile);
                //更新用户头像url
                String photo_url = applicationConstants.getServerHost() + ":" + applicationConstants.getServerPort()
                        + "/" + userid + "/" + targetFile.getName();
                //modifyUserInfo(phoneNum,"PROFILE_PHOTO",photo_url);
                updateUserInfo(phoneNum, "PROFILE_PHOTO", photo_url);
                jsonObject.put(AppResultConstants.MSG, SAVE_SUCCESS);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("拷贝头像到目标路径异常:" + e.getMessage());
                jsonObject.put(AppResultConstants.MSG, SERVER_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }
        }

        return jsonObject;
    }

    @Override
    public JSONObject sendVCtoPhoneNum(String phoneNum) {


        //发送到目标手机
        //String status = SMSUtil.sendSms();
        //根据status判断是否发送成功 ,并保存60秒后过期

        return null;
    }

    private Boolean verifyCode(String phoneNum, String code ,String type){
        Boolean flag = false;

        String reidsKey = String.format(type+":%s",phoneNum);
        Object codeCache = redisRepository.get(reidsKey);
        if(codeCache!=null){
            //找到验证码
            if(code.equals(codeCache.toString())){
                flag = true ;
            }
        }else{
            //未找到验证码
        }
        return flag;
    }


}
