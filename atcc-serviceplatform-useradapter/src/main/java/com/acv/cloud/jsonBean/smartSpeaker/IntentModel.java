package com.acv.cloud.jsonBean.smartSpeaker;

public class IntentModel {

    private String intentName;//意图
    private Long timesamp;//时间
    //private int count;//相同意图访问次数


    public IntentModel() {
    }

    public IntentModel(String intentName, long timesamp) {
        this.intentName = intentName;
        this.timesamp = timesamp;
        //this.count = count;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public long getTimesamp() {
        return timesamp;
    }

    public void setTimesamp(long timesamp) {
        this.timesamp = timesamp;
    }

//    public int getCount() {
//        return count;
//    }

//    public void setCount(int count) {
//        this.count = count;
//    }
}
