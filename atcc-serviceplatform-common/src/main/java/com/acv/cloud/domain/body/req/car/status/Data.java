package com.acv.cloud.domain.body.req.car.status;


import com.acv.cloud.domain.model.car.status.RequestVehicleState;

public class Data {

    private String type;

    private RequestVehicleState attributes;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RequestVehicleState getAttributes() {
        return attributes;
    }

    public void setAttributes(RequestVehicleState attributes) {
        this.attributes = attributes;
    }
}


