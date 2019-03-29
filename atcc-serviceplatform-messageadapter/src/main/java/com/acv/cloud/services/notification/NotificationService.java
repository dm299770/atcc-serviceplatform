package com.acv.cloud.services.notification;

import com.acv.cloud.models.mongdb.notification.requestJson.NotificationParams;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by liyang on 2018/12/18.
 */
public interface NotificationService {


    /**
     * 推送消息给单个app的所有IOS设备
     *
     * @return
     */
    JSONObject pushMsgDeviceAll(NotificationParams no);

    /**
     * @return
     */
    JSONObject pushMsgDeviceList(NotificationParams no);
}
