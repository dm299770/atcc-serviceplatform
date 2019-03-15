package com.acv.cloud.jsonBean.vehicle;


/**
 * 车辆基本信息
 */
public class VehicleInfoData {


    private String plateNum;          //车牌号
    private String vin;               //vin号
    private String engineNum;         //发动机号
    private String lastSixPhoneNum;   //预留手机号后六位
    //private String bindDate;          //绑定日期
    //private String unBindDate;        //解绑日期
    //private Integer isEffctive;       //有效性


    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public String getLastSixPhoneNum() {
        return lastSixPhoneNum;
    }

    public void setLastSixPhoneNum(String lastSixPhoneNum) {
        this.lastSixPhoneNum = lastSixPhoneNum;
    }
}
