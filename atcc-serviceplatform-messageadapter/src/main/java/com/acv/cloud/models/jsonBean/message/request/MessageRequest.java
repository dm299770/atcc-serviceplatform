package com.acv.cloud.models.jsonBean.message.request;

import com.acv.cloud.models.mongdb.notification.NotificationRequest;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 推送通知历史消息请求实体
 * <p>
 * Created by liyang on 2018/01/10.
 */
@Document(collection = "Notification")
public class MessageRequest {
//    //通知推送实体
    private NotificationRequest data;

    public NotificationRequest getData() {
        return data;
    }

    public void setData(NotificationRequest data) {
        this.data = data;
    }
}
