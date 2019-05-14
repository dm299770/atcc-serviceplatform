package com.acv.cloud.domain.model.sms;

public class Data {

    private String type;
    private Attributes attributes;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return attributes;
    }
}
