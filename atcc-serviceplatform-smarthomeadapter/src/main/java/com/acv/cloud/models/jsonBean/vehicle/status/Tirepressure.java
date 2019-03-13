package com.acv.cloud.models.jsonBean.vehicle.status;


/**
 * 胎压状态
 * @author guo.zj
 */
public class Tirepressure {

    //左前
    private Float FL;
    //右前
    private Float FR;
    //左后
    private Float BL;
    //右后
    private Float BR;

    private int flstate;

    private int frstate;

    private int blstate;

    private int brstate;

    public Float getFL() {
        return FL;
    }

    public void setFL(Float FL) {
        this.FL = FL;
    }

    public Float getFR() {
        return FR;
    }

    public void setFR(Float FR) {
        this.FR = FR;
    }

    public Float getBL() {
        return BL;
    }

    public void setBL(Float BL) {
        this.BL = BL;
    }

    public Float getBR() {
        return BR;
    }

    public void setBR(Float BR) {
        this.BR = BR;
    }

    public int getFlstate() {
        return flstate;
    }

    public void setFlstate(int flstate) {
        this.flstate = flstate;
    }

    public int getFrstate() {
        return frstate;
    }

    public void setFrstate(int frstate) {
        this.frstate = frstate;
    }

    public int getBlstate() {
        return blstate;
    }

    public void setBlstate(int blstate) {
        this.blstate = blstate;
    }

    public int getBrstate() {
        return brstate;
    }

    public void setBrstate(int brstate) {
        this.brstate = brstate;
    }
}

