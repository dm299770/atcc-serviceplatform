package com.acv.cloud.models.car;

import com.acv.cloud.jsonBean.vehiclestate.vehiclestatemodel.RequestVehicleState;

public class CarVehicleState {

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


