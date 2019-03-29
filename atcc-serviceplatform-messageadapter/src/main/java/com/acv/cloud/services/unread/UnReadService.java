package com.acv.cloud.services.unread;
import com.acv.cloud.models.mongdb.notification.requestJson.Notification;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by liyang on 2019/01/10.
 */
public interface UnReadService {

    /**
     *
     * @param no
     * @return
     */
    JSONObject updateUnRead(Notification no);
}
