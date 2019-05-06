/**
  * Copyright 2019 bejson.com 
  */
package com.acv.cloud.domain.body.req.userInfo.forgetPassword;

/**
 * Auto-generated: 2019-03-15 14:36:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Attributes {

    private String phoneNum;
    private String newPassword;
    private String code;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}