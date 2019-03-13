package com.acv.cloud.jsonBean.remote.remotemodel;

/**
 * 车锁/天窗
 * @author guo.zj
 */
public class EVVehicleCtrl {
    private int action;
    private String vin;
    private int smsflag;


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }


    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getSmsflag() {
        return smsflag;
    }

    public void setSmsflag(int smsflag) {
        this.smsflag = smsflag;
    }
}
