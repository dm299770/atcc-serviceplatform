package com.acv.cloud.jsonBean.vehiclestate.vehiclestatemodel;

/**
 *  EV车辆状态请求
 * @author guo.zj
 */
public class RequestVehicleState {
    //车架号
    private String vin;
   //请求方式  0实时 1数据库
    private int mode;


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
