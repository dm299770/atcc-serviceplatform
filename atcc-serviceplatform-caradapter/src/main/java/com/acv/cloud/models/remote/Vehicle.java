package com.acv.cloud.models.remote;

/**
 * 车锁/天窗
 * @author guo.zj
 */
public class Vehicle {
    private String vin;

    private int smsflag;


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getSmsflag() {
        return smsflag;
    }

    public void setSmsflag(int smsflag) {
        this.smsflag = smsflag;
    }
}
