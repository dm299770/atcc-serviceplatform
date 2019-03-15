package com.acv.cloud.services.device;

import com.alibaba.fastjson.JSONObject;

public interface DeviceSerivce {

    /**获取缓存中的设备推送码*/
    public JSONObject getDeviceNoFromCaChe(String userid , String deviceNo);

}
