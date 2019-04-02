package com.acv.cloud.jsonBean.fegin.messageadapter.sms;




/**
 * 短信实体类
 * Created by liyang on 2018/12/24.
 */
public class Attributes {
    private String phoneNum;//手机号
    private String content;//内容
    private String createDate;//发送日期

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
