package com.acv.cloud.repository.redistemplate.impl;

import com.acv.cloud.repository.redistemplate.INotificationDao;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by liyang on 2018/12/19.
 */
@Repository
public class NotificationDaoImpl implements INotificationDao {
    @Autowired
    private RedisRepository redisRepository;


    @Override
    public String getDeviceToken(String userId) {
        redisRepository.init(1);
        String deviceId = String.valueOf(redisRepository.get("account_device:" + userId + ":IOS"));
        return deviceId;
    }

    @Override
    public String getDeviceAccont(String deviceId) {
        redisRepository.init(1);
        String deviceAccount = String.valueOf(redisRepository.get("device_account:IOS:" + deviceId));
        return deviceAccount;
    }


}
