package com.acv.cloud.models.jsonBean.smartSpeaker;

import java.io.Serializable;

public class TaskRequest<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId; //session id
    private String intentName;//意图名

    private Long botId;//应用id
    private String utterance;//用户输入语句
    private Long skillId;//技能id
    private String skillName;//技能名

    private Long intentParameterId;//意图参数id
    private String intentParameterName;//意图参数名
    private String standardValue;//原始slot归一化后的值
    private String originalValue;//原始句子中抽取出来的未做处理的slot值
    private Integer liveTime;//该slot已生存时间（会话轮数）
    private Long createTimeStamp;//该slot产生的时间点


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

    public Long getIntentParameterId() {
        return intentParameterId;
    }

    public void setIntentParameterId(Long intentParameterId) {
        this.intentParameterId = intentParameterId;
    }

    public String getIntentParameterName() {
        return intentParameterName;
    }

    public void setIntentParameterName(String intentParameterName) {
        this.intentParameterName = intentParameterName;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public Integer getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Integer liveTime) {
        this.liveTime = liveTime;
    }

    public Long getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getUtterance() {
        return utterance;
    }

    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }


    public Long getBotId() {
        return botId;
    }

    public void setBotId(Long botId) {
        this.botId = botId;
    }

    public String toString() {
        return "TaskQuery " +
                "[" +
                "sessionId=" + this.sessionId +
                ", intentName=" + this.intentName +
                ", utterance=" + this.utterance +
                ", skillId=" + this.skillId +
                ", skillName=" + this.skillName +
                ", botId=" + this.botId +
                ", intentParameterId=" + this.intentParameterId +
                ", intentParameterName=" + this.intentParameterName +
                ", standardValue=" + this.standardValue +
                ", originalValue=" + this.originalValue +
                ", liveTime=" + this.liveTime +
                ", createTimeStamp=" + this.createTimeStamp +
                "]";
    }
}