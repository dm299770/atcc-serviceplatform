package com.acv.cloud.models.vehiclestate;


/**
 * 空调状态
 * @author guo.zj
 */
public class AirConditionStatus {
       //快关
       private int onoff;
       //温度状态
       private int model;
       //车内温度
       private Float internaltemperture;
       //左空调温度
       private Float temperturel;
       //右空调温度
       private Float temperturer;
       //空调风速
       private int windowspeed;
       //预热模式
       private int defrost;
       //定时关闭
       private int timing;

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public Float getInternaltemperture() {
        return internaltemperture;
    }

    public void setInternaltemperture(Float internaltemperture) {
        this.internaltemperture = internaltemperture;
    }

    public Float getTemperturel() {
        return temperturel;
    }

    public void setTemperturel(Float temperturel) {
        this.temperturel = temperturel;
    }

    public Float getTemperturer() {
        return temperturer;
    }

    public void setTemperturer(Float temperturer) {
        this.temperturer = temperturer;
    }

    public int getWindowspeed() {
        return windowspeed;
    }

    public void setWindowspeed(int windowspeed) {
        this.windowspeed = windowspeed;
    }

    public int getDefrost() {
        return defrost;
    }

    public void setDefrost(int defrost) {
        this.defrost = defrost;
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }
}

