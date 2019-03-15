package com.acv.cloud.services.device.impl;

import com.acv.cloud.repository.redistemplate.IUserDao;
import com.acv.cloud.services.device.DeviceSerivce;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class DeviceServcieImpl implements DeviceSerivce {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserDao userDao;


    @Override
    public JSONObject getDeviceNoFromCaChe(String userid, String deviceNo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceNo",userDao.getDeviceNo(userid,deviceNo));
        return jsonObject;
    }
}
