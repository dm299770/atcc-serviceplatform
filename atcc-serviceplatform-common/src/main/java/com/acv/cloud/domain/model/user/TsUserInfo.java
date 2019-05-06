package com.acv.cloud.domain.model.user;

import java.util.Date;

/**
 * 用户详细信息
 *
 * @author leo.
 */
public class TsUserInfo implements java.io.Serializable {

    private String userId;
    private String profilePhoto;
    private String nickName;
    private String realName;
    private String sexual;
    private String emeyContact;
    private int isEffctive;
    private Date createDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }


    public TsUserInfo() {
    }

    public TsUserInfo(String userId, String nickName, String realName, String sexual, String emeyContact,String profilePhoto) {
        this.userId = userId;
        this.nickName = nickName;
        this.realName = realName;
        this.sexual = sexual;
        this.emeyContact = emeyContact;
        this.profilePhoto = profilePhoto;
    }
}
