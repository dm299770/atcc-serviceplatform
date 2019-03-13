package com.acv.cloud.repository.redistemplate;
/**
 * @description:操作redis，对redisTemplate实现
 * @author:@leo.
 * */

public interface IUserDao {
    void addDeviceNo(String phoneNum, String deiviceNo);
    void updateDeviceNo(String phoneNum, String deiviceNo);

}
