package com.acv.cloud.models.appvehiclestate;



/**
 * 车门状态
 * @author guo.zj
 */
public class DoorStatus {

    //左前
    private int fl;
    //右前
    private int fr;
    //左后
    private int rl;
    //右后
    private int rr;

    public int getFl() {
        return fl;
    }

    public void setFl(int fl) {
        this.fl = fl;
    }

    public int getFr() {
        return fr;
    }

    public void setFr(int fr) {
        this.fr = fr;
    }

    public int getRl() {
        return rl;
    }

    public void setRl(int rl) {
        this.rl = rl;
    }

    public int getRr() {
        return rr;
    }

    public void setRr(int rr) {
        this.rr = rr;
    }
}

