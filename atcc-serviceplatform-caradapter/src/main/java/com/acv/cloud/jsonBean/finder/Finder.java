package com.acv.cloud.jsonBean.finder;

import java.util.Date;

/**
 * 远程寻车
 * @author guo.zj
 */
public class Finder {
    private String type;
    private String vin;
   //属性get（）set（）方法
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
}
