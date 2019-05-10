package com.acv.cloud.domain.body.req.car.control;


import com.acv.cloud.domain.model.car.control.Control;

public class Data {

    private String type;

    private Control attributes;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Control getAttributes() {
        return attributes;
    }

    public void setAttributes(Control attributes) {
        this.attributes = attributes;
    }
}


