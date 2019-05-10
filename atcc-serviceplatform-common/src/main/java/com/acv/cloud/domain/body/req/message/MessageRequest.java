package com.acv.cloud.domain.body.req.message;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 推送通知历史消息请求实体
 * <p>
 * Created by liyang on 2018/01/10.
 */
@Document(collection = "Notification")
public class MessageRequest {
    //    //通知推送实体
    private NotificationRequest attributes;
    private String type;

    public NotificationRequest getAttributes() {
        return attributes;
    }

    public void setAttributes(NotificationRequest attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
