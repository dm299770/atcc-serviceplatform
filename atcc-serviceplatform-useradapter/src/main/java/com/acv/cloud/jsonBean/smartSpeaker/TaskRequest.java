package com.acv.cloud.jsonBean.smartSpeaker;

public class TaskRequest<T> {
    private static final long serialVersionUID = 1L;

    private String sessionId; //session id
    private String intentName;//意图
    private String isConfirmed;//是否开启确认意图
    private T value;//属性

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
