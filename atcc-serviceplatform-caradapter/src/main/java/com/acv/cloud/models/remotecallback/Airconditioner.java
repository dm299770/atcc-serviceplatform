package com.acv.cloud.models.remotecallback;

public class Airconditioner {

     private int temperature;
     private int speed;
     private long offtime;


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public long getOfftime() {
        return offtime;
    }

    public void setOfftime(long offtime) {
        this.offtime = offtime;
    }
}
