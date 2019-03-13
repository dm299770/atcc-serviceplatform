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
    public String getDeviceToken(String phoneNums) {
        String token = String.valueOf(redisRepository.get(phoneNums));
        return token;
    }
}
