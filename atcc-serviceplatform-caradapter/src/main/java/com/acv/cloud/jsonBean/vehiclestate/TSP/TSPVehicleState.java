package com.acv.cloud.jsonBean.vehiclestate.TSP;


import com.acv.cloud.models.tspvehiclemodel.TspTirepressure;

/**
 *EV车辆状态
 * @author guo.zj
 */
public class TSPVehicleState {
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
    private TspTirepressure tirepressure;
    //车锁状态
    private String doorstatus;
    //天窗状态
    private int sunroofstatus;
    //续航里程
    private Float usablekm;
    //百公里耗电
    private Float consumerate;
    //经度
    private double lon;
    //维度
    private double lat;

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


    public int getSunroofstatus() {
        return sunroofstatus;
    }

    public void setSunroofstatus(int sunroofstatus) {
        this.sunroofstatus = sunroofstatus;
    }

    public Float getResbatterycap() {
        return resbatterycap;
    }

    public void setResbatterycap(Float resbatterycap) {
        this.resbatterycap = resbatterycap;
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


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDoorstatus() {
        return doorstatus;
    }

    public void setDoorstatus(String doorstatus) {
        this.doorstatus = doorstatus;
    }

    public TspTirepressure getTirepressure() {
        return tirepressure;
    }

    public void setTirepressure(TspTirepressure tirepressure) {
        this.tirepressure = tirepressure;
    }
}
