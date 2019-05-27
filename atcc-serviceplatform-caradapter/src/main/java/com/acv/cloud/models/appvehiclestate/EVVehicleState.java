package com.acv.cloud.models.appvehiclestate;


/**
 *EV车辆状态APP端
 * @author guo.zj
 */
public class EVVehicleState {
    //胎压
    private Tirepressure tirePressure;
    //剩余电量
    private Float currentBattery;
    //驾驶状态
    private String drivingStatus;
    //充电状态
    private String chargingStatus;
    //后备箱状态
    private int trunkStatus;
    //车窗
    private WindowStatus windowStatus;
    //车门（锁）状态
    private DoorStatus doorStatus;
    //天窗状态
    private int sunroofStatus;
    //续航里程
    private Float usableMileage;
    //百公里耗电
    private Float consumeRate;
    //上报时间
    private long uploadTime;
    //经度
    private double lon;
    //纬度
    private double lat;


    public Tirepressure getTirePressure() {
        return tirePressure;
    }

    public void setTirePressure(Tirepressure tirePressure) {
        this.tirePressure = tirePressure;
    }

    public Float getCurrentBattery() {
        return currentBattery;
    }

    public void setCurrentBattery(Float currentBattery) {
        this.currentBattery = currentBattery;
    }

    public String getDrivingStatus() {
        return drivingStatus;
    }

    public void setDrivingStatus(String drivingStatus) {
        this.drivingStatus = drivingStatus;
    }

    public String getChargingStatus() {
        return chargingStatus;
    }

    public void setChargingStatus(String chargingStatus) {
        this.chargingStatus = chargingStatus;
    }

    public int getTrunkStatus() {
        return trunkStatus;
    }

    public void setTrunkStatus(int trunkStatus) {
        this.trunkStatus = trunkStatus;
    }

    public WindowStatus getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(WindowStatus windowStatus) {
        this.windowStatus = windowStatus;
    }

    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }

    public int getSunroofStatus() {
        return sunroofStatus;
    }

    public void setSunroofStatus(int sunroofStatus) {
        this.sunroofStatus = sunroofStatus;
    }

    public Float getUsableMileage() {
        return usableMileage;
    }

    public void setUsableMileage(Float usableMileage) {
        this.usableMileage = usableMileage;
    }

    public Float getConsumeRate() {
        return consumeRate;
    }

    public void setConsumeRate(Float consumeRate) {
        this.consumeRate = consumeRate;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
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
}
