package com.acv.cloud.domain.model.car.control;


public class Control {
    //vin
    private String vin;
    //操作类型
    private String cmdid;
    //是否推送
    private int smsflag;
    //启动关闭
    private int onoff;
    //空调
    private AirCondition airconditioner;


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

    public int getSmsflag() {
        return smsflag;
    }

    public void setSmsflag(int smsflag) {
        this.smsflag = smsflag;
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


