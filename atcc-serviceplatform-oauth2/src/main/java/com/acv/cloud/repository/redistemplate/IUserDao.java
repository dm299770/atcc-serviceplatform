package com.acv.cloud.repository.redistemplate;

/**
 * @description:操作redis，对redisTemplate实现
 * @author:@leo.
 */

public interface IUserDao {

    void addDeviceNo(String phoneNum, String deiviceNo);

    void updateDeviceNo(String phoneNum, String deiviceNo);

    /**
     * 添加第三方登录授权码及账户
     */
    void addAuthCode(String authCode, String phoneNUm);

    /**
     * 删除第三方账户授权码
     */
    void deleteAuthCode(String authCode);

    /**
     * 根据授权码获得手机账号
     */
    String getPhoneNumByAuthCode(String authCode);

    /**
     * 删除设备号
     */
    void deleteDeviceNo(String phoneNum);
}
