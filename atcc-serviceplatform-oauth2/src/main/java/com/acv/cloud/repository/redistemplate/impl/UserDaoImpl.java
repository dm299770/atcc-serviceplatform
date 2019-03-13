package com.acv.cloud.repository.redistemplate.impl;

import com.acv.cloud.frame.constants.OauthConstants;
import com.acv.cloud.repository.redistemplate.IUserDao;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private RedisRepository redisRepository;

    private int redisDb = OauthConstants.reidsdb;


    @Override
    public void addDeviceNo(String phoneNum, String deiviceNo) {
        redisRepository.init(redisDb);
        redisRepository.set(phoneNum, deiviceNo);
    }

    @Override
    public void updateDeviceNo(String phoneNum, String deiviceNo) {
        redisRepository.init(redisDb);
        redisRepository.remove(phoneNum);
        redisRepository.set(phoneNum, deiviceNo);
    }

    @Override
    public void addAuthCode(String authCode, String phoneNum) {
        redisRepository.init(redisDb);
        redisRepository.set(authCode, phoneNum);
    }

    @Override
    public void deleteAuthCode(String phoneNum) {
        redisRepository.init(redisDb);
        redisRepository.remove(phoneNum);
    }

    @Override
    public String getPhoneNumByAuthCode(String authCode) {
        redisRepository.init(redisDb);
        Object o = redisRepository.get(authCode);
        if(o!=null){
            return o.toString();
        }else{
            return null;
        }
    }

    @Override
    public void deleteDeviceNo(String phoneNum) {
        redisRepository.init(redisDb);
        redisRepository.remove(phoneNum);
    }
}
