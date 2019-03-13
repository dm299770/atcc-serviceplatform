package com.acv.cloud.models.remote.tspvehiclemodel;

import com.acv.cloud.models.remote.AirCondition;

public class TspAirConditioner {

    private String vin;
    private String cmdid;
    private String requestid;
    private int onoff;
    private AirCondition  airconditioner;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCmdid() {
        return cmdid;
    }

    public void setCmdid(String cmdid) {
        this.cmdid = cmdid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public AirCondition getAirconditioner() {
        return airconditioner;
    }

    public void setAirconditioner(AirCondition airconditioner) {
        this.airconditioner = airconditioner;
    }
}
