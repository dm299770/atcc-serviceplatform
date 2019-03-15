package com.acv.cloud.jsonBean.user;

public class UserInfoRequsetBody {
    private String type;//属性类型
    private String value;//属性值

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
