package com.acv.cloud.models.sys;

import java.util.Date;

/**
 * 用户bean
 * @author leo.
 */
public class TsUser implements java.io.Serializable {

    private String userId;
    private String phoneNum;
    private String password;
    private int isEffctive;
    private Date createDate;
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsEffctive() {
        return isEffctive;
    }

    public void setIsEffctive(int isEffctive) {
        this.isEffctive = isEffctive;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public TsUser(String userId, String phoneNum, String password, int isEffctive, Date createTime, String token) {
//        this.userId = userId;
//        this.phoneNum = phoneNum;
//        this.password = password;
//        this.isEffctive = isEffctive;
//        this.createDate=createTime;
//
//        this.token = token;
//    }
}
