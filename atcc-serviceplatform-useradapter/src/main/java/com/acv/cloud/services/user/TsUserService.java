package com.acv.cloud.services.user;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.models.sys.SysUser;
import com.acv.cloud.models.sys.TmChargeAccount;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.models.sys.TsUserInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息
 *
 * @author leo
 */
public interface TsUserService {

    /**
     * 根据id查找用户
     *
     * @param id 用户主键
     * @return
     */
    TsUser findById(String id);

    /**
     * 根据用户电话号查找用户
     *
     * @param phoneNum 用户电话号
     * @return
     */
    UserInfo findEffctiveUserInfoByPhoneNum(String phoneNum);
    /**
     * 根据用户主键查找用户
     *
     * @param uuid 用户主键
     * @return
     */
    UserInfo findEffctiveUserInfoById(String uuid);

    /**
     * 根据用户电话号查找用户
     *
     * @param phoneNum 用户电话号
     * @return
     */
    TsUser findEffctiveByPhoneNum(String phoneNum);

    /**
     * 保存用户
     *
     * @param bean 用户bean
     */
    void save(TsUser bean);

    /**
     * 保存用户基本信息
     *
     * @param bean 用户bean
     */
    void saveUserInfo(TsUserInfo bean);

    /**
     * 保存用户账号信息
     */
    void saveUserAccount(TmChargeAccount bean);

    /**
     * 删除用户
     *
     * @param userIds 用户主键数组
     */
    void delete(String[] userIds);

    /**
     * 更新用户属性（每次更新一项）
     *
     * @param userId   用户id
     * @param type     要修改的属性名
     * @param value    要修改的属性值
     */
    void updateUserInfo(String userId, String type, String value);
//
//    /**
//     * 修改用户密码
//     *
//     * @param userId      用户主键
//     * @param oldPassword 旧密码
//     * @param newPassword 新密码
//     */
//    void updatePassword(String userId, String oldPassword, String newPassword);

    /**
     * 修改用户密码
     *
     * @param phoneNum    用户电话号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(String phoneNum, String oldPassword, String newPassword);

//    /**
//     * 重置用户密码
//     *
//     * @param userId
//     * @param newPassword
//     */
//    void reSetPassword(String userId, String newPassword);

    /**
     * 重置用户密码
     *
     * @param phoneNum
     * @param newPassword
     */
    void reSetPassword(String phoneNum, String newPassword);


    /**
     * 注册用户
     *
     * @param phoneNum 用户名
     * @param password 密码
     * @param token    备用字段（与Kamereon系统集成时使用）
     * @return 返回信息
     */
    JSONObject registeredUser(String phoneNum, String password, String token);

    /**
     * 更新用户信息
     *
     * @param userId   用户id
     * @param type     需要修改的属性名
     * @param value    修改后的值
     * @return 返回信息
     */
    JSONObject modifyUserInfo(String userId, String type, String value);

    /**
     * 更新用户密码
     *
     * @param userId    用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 返回信息
     */
    JSONObject modifyUserPassword(String userId, String oldPassword, String newPassword);

    /**
     * 重置密码
     *
     * @param phoneNum    用户电话号
     * @param newPassword 新密码
     * @return 返回信息
     */
    JSONObject resetUserPassword(String phoneNum, String newPassword,String code);

    /**
     * 上传更新用户头像
     *
     * @param userid   用户id
     * @param phoneNum 手机号
     * @param file     头像名
     * @return
     */

    JSONObject setProfilePhoto(String userid, String phoneNum, MultipartFile file);

    /**
     * 生成并向目标手机号发送验证码
     *
     * @param phoneNum 用户手机号
     * @return
     */
    JSONObject sendVCtoPhoneNum(String phoneNum);
}

