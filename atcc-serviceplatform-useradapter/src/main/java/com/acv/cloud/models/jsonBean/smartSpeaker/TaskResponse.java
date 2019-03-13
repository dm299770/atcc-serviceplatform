package com.acv.cloud.models.jsonBean.smartSpeaker;

public class TaskResponse<T> {

    private boolean open_mic;//是否关闭麦克风
    private boolean is_session_end;//是否结束会话
    private String speaker;//反馈信息
    private T value;

    public TaskResponse() {

    }

    public TaskResponse(boolean open_mic, boolean is_session_end, String speaker) {
        this.open_mic = open_mic;
        this.is_session_end = is_session_end;
        this.speaker = speaker;
    }

    public boolean isOpen_mic() {
        return open_mic;
    }

    public void setOpen_mic(boolean open_mic) {
        this.open_mic = open_mic;
    }

    public boolean isIs_session_end() {
        return is_session_end;
    }

    public void setIs_session_end(boolean is_session_end) {
        this.is_session_end = is_session_end;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
