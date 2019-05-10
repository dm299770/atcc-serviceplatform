package com.acv.cloud.domain.model.car.status;


/**
 *EV车辆状态
 * @author guo.zj
 */
public class EVVehicleState {
    //请求ID
    private String requestid;
    //剩余电量
    private Float resbatterycap;
    //后备箱状态
    private int trunkstatus;
    //驾驶状态
    private String drivingstatus;
    //充电状态
    private String chargingstatus;
    //胎压
    private Tirepressure tirepressure;
    //车锁状态
    private int doorstatus;
    //天窗状态
    private int sunroofstatus;
    //上报时间
    private long updatetime;
    //车窗
    private WindowStatus windowstatus;
    //续航里程
    private Float usablekm;
    //百公里耗电
    private Float consumerate;
    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }



    public int getTrunkstatus() {
        return trunkstatus;
    }

    public void setTrunkstatus(int trunkstatus) {
        this.trunkstatus = trunkstatus;
    }

    public String getDrivingstatus() {
        return drivingstatus;
    }

    public void setDrivingstatus(String drivingstatus) {
        this.drivingstatus = drivingstatus;
    }

    public String getChargingstatus() {
        return chargingstatus;
    }

    public void setChargingstatus(String chargingstatus) {
        this.chargingstatus = chargingstatus;
    }


    public int getDoorstatus() {
        return doorstatus;
    }

    public void setDoorstatus(int doorstatus) {
        this.doorstatus = doorstatus;
    }

    public int getSunroofstatus() {
        return sunroofstatus;
    }

    public void setSunroofstatus(int sunroofstatus) {
        this.sunroofstatus = sunroofstatus;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }


    public Float getResbatterycap() {
        return resbatterycap;
    }

    public void setResbatterycap(Float resbatterycap) {
        this.resbatterycap = resbatterycap;
    }

    public WindowStatus getWindowstatus() {
        return windowstatus;
    }

    public void setWindowstatus(WindowStatus windowstatus) {
        this.windowstatus = windowstatus;
    }

    public Tirepressure getTirepressure() {
        return tirepressure;
    }

    public void setTirepressure(Tirepressure tirepressure) {
        this.tirepressure = tirepressure;
    }

    public Float getUsablekm() {
        return usablekm;
    }

    public void setUsablekm(Float usablekm) {
        this.usablekm = usablekm;
    }

    public Float getConsumerate() {
        return consumerate;
    }

    public void setConsumerate(Float consumerate) {
        this.consumerate = consumerate;
    }
}
