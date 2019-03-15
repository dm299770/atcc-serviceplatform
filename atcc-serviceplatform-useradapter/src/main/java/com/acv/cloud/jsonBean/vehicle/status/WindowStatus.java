package com.acv.cloud.jsonBean.vehicle.status;


/**
 * 车窗状态
 * @author guo.zj
 */
public class WindowStatus {

    //左前
    private int FL;
    //右前
    private int FR;
    //左后
    private int BL;

    public int getFL() {
        return FL;
    }

    public void setFL(int FL) {
        this.FL = FL;
    }

    public int getFR() {
        return FR;
    }

    public void setFR(int FR) {
        this.FR = FR;
    }

    public int getBL() {
        return BL;
    }

    public void setBL(int BL) {
        this.BL = BL;
    }

    public int getBR() {
        return BR;
    }

    public void setBR(int BR) {
        this.BR = BR;
    }

    //右后
    private int BR;

}

