package com.acv.cloud.models.vehiclestate;


/**
 * 胎压状态
 * @author guo.zj
 */
public class TspTirepressure {

    //左前
    private Float FL;
    //右前
    private Float FR;
    //左后
    private Float BL;
    //右后
    private Float BR;

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
}

