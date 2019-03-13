package com.acv.cloud.models.jsonBean.smartSpeaker;


import com.acv.cloud.Enum.ExecuteCode;

public class TaskResponse<T> {
    private String returnCode;//“0”默认表示成功，其他不成功的字段自己可以确定
    private String reply;//回复播报语句
    private ExecuteCode executeCode;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public ExecuteCode getExecuteCode() {
        return executeCode;
    }

    public void setExecuteCode(ExecuteCode executeCode) {
        this.executeCode = executeCode;
    }

    public TaskResponse() {
    }

    public TaskResponse(String returnCode, String reply, ExecuteCode executeCode) {
        this.returnCode = returnCode;
        this.reply = reply;
        this.executeCode = executeCode;
    }
}
