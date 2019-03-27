package com.acv.cloud.models.jsonBean.vehicle.request;

/**
 * 车窗状态
 * @author guo.zj
 */
public class RequestVehicleState {
    //车架号
    private String vin;
    //总里程
    private int model;


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }


    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
