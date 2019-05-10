package com.acv.cloud.domain.model.car.control;

/**
 * 空调
 * @author guo.zj
 */
public class AirCondition {

    //温度状态
    private int model;
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

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public Float getTemperturel() {
        return temperturel;
    }

    public void setTemperturel(Float temperturel) {
        this.temperturel = temperturel;
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

    public Float getTemperturer() {
        return temperturer;
    }

    public void setTemperturer(Float temperturer) {
        this.temperturer = temperturer;
    }
}
