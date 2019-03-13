package com.acv.cloud.dto.sys;

import java.util.Date;

public class UserInfo implements java.io.Serializable{

    private String userId;
    private String phoneNum;
    private String password;
    private int isEffctive;
    private String profilePhoto;
    private String nickName;
    private String realName;
    private String sexual;
    private String emeyContact;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSexual() {
        return sexual;
    }

    public void setSexual(String sexual) {
        this.sexual = sexual;
    }

    public String getEmeyContact() {
        return emeyContact;
    }

    public void setEmeyContact(String emeyContact) {
        this.emeyContact = emeyContact;
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
}
