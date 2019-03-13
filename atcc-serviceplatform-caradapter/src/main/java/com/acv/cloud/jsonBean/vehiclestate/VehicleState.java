package com.acv.cloud.jsonBean.vehiclestate;

/**
 * 远程寻车
 * @author guo.zj
 */
public class VehicleState {
    //续航里程
    private String endurancemileage;
   //总里程
    private int mileage;
   //剩余油量
    private String oilconsumption;
    //行驶里程
    private String roadhaul;
    //行驶状态
    private String drivingstate;
    //百公里油耗
    private String fcphk;
    //形式百分比
    private int partmileage;

    public String getTire() {
        return tire;
    }

    public void setTire(String tire) {
        this.tire = tire;
    }

    //胎压状态
    private String tire;


    public int getPartmileage() {
        return partmileage;
    }
    public void setPartmileage(int partmileage) {
        this.partmileage = partmileage;
    }

    public String getEndurancemileage() {
        return endurancemileage;
    }

    public void setEndurancemileage(String endurancemileage) {
        this.endurancemileage = endurancemileage;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getOilconsumption() {
        return oilconsumption;
    }

    public void setOilconsumption(String oilconsumption) {
        this.oilconsumption = oilconsumption;
    }

    public String getRoadhaul() {
        return roadhaul;
    }

    public void setRoadhaul(String roadhaul) {
        this.roadhaul = roadhaul;
    }

    public String getDrivingstate() {
        return drivingstate;
    }

    public void setDrivingstate(String drivingstate) {
        this.drivingstate = drivingstate;
    }

    public String getFcphk() {
        return fcphk;
    }

    public void setFcphk(String fcphk) {
        this.fcphk = fcphk;
    }

}
