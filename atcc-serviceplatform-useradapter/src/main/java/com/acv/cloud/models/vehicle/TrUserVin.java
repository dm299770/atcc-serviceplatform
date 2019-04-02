package com.acv.cloud.models.vehicle;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 绑定车辆基本信息
 */
public class TrUserVin implements java.io.Serializable {


    private String userId;                //用户id
    private String vin;                   //vin号
    private String plateNum;              //车牌号
    private String lastSixPhoneNum;      //手机号预留后六位
    private String engineNum;             //发动机号
    private Integer defaultVehicle;       //默认车辆 1-是 ,0-否
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private Date bindingDate;             //绑定时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private Date unBindingDate;           //解绑时间
    private Integer isEffctive;           //有效性
    private Date createDate;              //创建时间

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getLastSixPhoneNum() {
        return lastSixPhoneNum;
    }

    public void setLastSixPhoneNum(String lastSixPhoneNum) {
        this.lastSixPhoneNum = lastSixPhoneNum;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public Date getBindingDate() {
        return bindingDate;
    }

    public void setBindingDate(Date bindingDate) {
        this.bindingDate = bindingDate;
    }

    public Date getUnBindingDate() {
        return unBindingDate;
    }

    public void setUnBindingDate(Date unBindingDate) {
        this.unBindingDate = unBindingDate;
    }

    public Integer getIsEffctive() {
        return isEffctive;
    }

    public void setIsEffctive(Integer isEffctive) {
        this.isEffctive = isEffctive;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDefaultVehicle() {
        return defaultVehicle;
    }

    public void setDefaultVehicle(Integer defaultVehicle) {
        this.defaultVehicle = defaultVehicle;
    }
}
