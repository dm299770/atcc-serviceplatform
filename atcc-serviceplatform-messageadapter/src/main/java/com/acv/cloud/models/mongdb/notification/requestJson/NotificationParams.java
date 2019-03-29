package com.acv.cloud.models.mongdb.notification.requestJson;

public class NotificationParams {
    private Datal data;

    public void setData(Datal data) {
        this.data = data;
    }

    public Datal getData() {
        return data;
    }

    public NotificationParams(Datal data) {
        this.data = data;
    }

}
