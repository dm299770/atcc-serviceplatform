package com.acv.cloud.domain.body.req.notification;


public class Datal {
    private String type;
    private Notification attributes;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAttributes(Notification attributes) {
        this.attributes = attributes;
    }

    public Notification getAttributes() {
        return attributes;
    }

    public Datal(Notification attributes) {
        this.attributes = attributes;
    }
}
