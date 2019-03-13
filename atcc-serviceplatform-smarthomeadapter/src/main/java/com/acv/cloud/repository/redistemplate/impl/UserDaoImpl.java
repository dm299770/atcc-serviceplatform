package com.acv.cloud.repository.redistemplate.impl;

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
    public void updateDeviceNo(String phoneNum, String deiviceNo) {
        redisRepository.remove(phoneNum);
        redisRepository.set(phoneNum, deiviceNo);
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
