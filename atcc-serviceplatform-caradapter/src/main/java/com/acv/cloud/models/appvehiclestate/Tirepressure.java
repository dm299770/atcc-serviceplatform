package com.acv.cloud.models.appvehiclestate;


/**
 * APP胎压状态
 * @author guo.zj
 */
public class  Tirepressure {

    //左前
    private Float fl;
    //右前
    private Float fr;
    //左后
    private Float rl;
    //右后
    private Float rr;

    private int flstate;

    private int frstate;

    private int rlstate;

    private int rrstate;


    public Float getFl() {
        return fl;
    }

    public void setFl(Float fl) {
        this.fl = fl;
    }

    public Float getFr() {
        return fr;
    }

    public void setFr(Float fr) {
        this.fr = fr;
    }

    public Float getRl() {
        return rl;
    }

    public void setRl(Float rl) {
        this.rl = rl;
    }

    public Float getRr() {
        return rr;
    }

    public void setRr(Float rr) {
        this.rr = rr;
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

    public int getRlstate() {
        return rlstate;
    }

    public void setRlstate(int rlstate) {
        this.rlstate = rlstate;
    }

    public int getRrstate() {
        return rrstate;
    }

    public void setRrstate(int rrstate) {
        this.rrstate = rrstate;
    }
}

