package com.acv.cloud.domain.model.car.status;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 胎压状态
 * @author guo.zj
 */
public class Tirepressure {

    //左前
    @JSONField(name = "FL")
    private Float FL;
    //右前
    @JSONField(name = "FR")
    private Float FR;
    //左后
    @JSONField(name = "BL")
    private Float BL;
    //右后
    @JSONField(name = "BR")
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

