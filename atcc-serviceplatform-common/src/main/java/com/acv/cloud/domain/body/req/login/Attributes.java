/**
  * Copyright 2019 bejson.com 
  */
package com.acv.cloud.domain.body.req.login;

/**
 * Auto-generated: 2019-03-12 14:36:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Attributes {

    private String phoneNum;
    private String password;
    private String deviceNo;
    private String deviceType;


    public Attributes(String phoneNum, String password, String deviceNo, String deviceType) {
        this.phoneNum = phoneNum;
        this.password = password;
        this.deviceNo = deviceNo;
        this.deviceType = deviceType;
    }

    public Attributes() {
    }

    public void setPhoneNum(String phoneNum) {
         this.phoneNum = phoneNum;
     }
     public String getPhoneNum() {
         return phoneNum;
     }

    public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
     }

    public void setDeviceNo(String deviceNo) {
         this.deviceNo = deviceNo;
     }
     public String getDeviceNo() {
         return deviceNo;
     }

    public void setDeviceType(String deviceType) {
         this.deviceType = deviceType;
     }
     public String getDeviceType() {
         return deviceType;
     }

}