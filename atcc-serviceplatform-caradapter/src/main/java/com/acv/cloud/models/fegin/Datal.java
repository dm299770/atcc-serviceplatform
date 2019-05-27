package com.acv.cloud.models.fegin;

public class Datal {
    private String type;
    private  Notification attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Notification getAttributes() {
        return attributes;
    }

    public void setAttributes(Notification attributes) {
        this.attributes = attributes;
    }

    public Datal(Notification attributes) {
        this.attributes = attributes;
    }
}
