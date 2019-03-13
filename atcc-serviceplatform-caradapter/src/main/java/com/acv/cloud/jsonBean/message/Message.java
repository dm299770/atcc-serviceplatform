package com.acv.cloud.jsonBean.message;

/**
 * 消息中心查询消息
 * @author guo.zj
 */
public class Message {
    private String type;
    private String phone;
    private String messagetype;
    //属性get（）set（）方法
    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
