package com.acv.cloud.repository.redistemplate.impl;

import com.acv.cloud.frame.constants.RedisConstants;
import com.acv.cloud.repository.redistemplate.IUserDao;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private RedisRepository redisRepository;


    @Override
    public void addDeviceNo(String phoneNum, String deiviceNo) {
        redisRepository.set(phoneNum, deiviceNo);
    }

    @Override
    public void updateDeviceNo(String userid, String deviceType,String deiviceNo) {

        String device_account = String.format(RedisConstants.DEVICE_ACCOUNT+":%s",deiviceNo);
        String account_device = String.format(RedisConstants.ACCOUNT_DEVICE+":%s:%s",userid,deviceType);

        redisRepository.remove(device_account);
        redisRepository.set(device_account, userid);
        redisRepository.set(account_device, deiviceNo);

    }

    @Override
    public void addAuthCode(String authCode, String phoneNum) {
        redisRepository.set(authCode, phoneNum);
    }

    @Override
    public void deleteAuthCode(String phoneNum) {
        redisRepository.remove(phoneNum);
    }

    @Override
    public String getPhoneNumByAuthCode(String authCode) {
        return redisRepository.get(authCode).toString();
    }

    @Override
    public void deleteDeviceNo(String phoneNum) {
        redisRepository.remove(phoneNum);
    }
}
