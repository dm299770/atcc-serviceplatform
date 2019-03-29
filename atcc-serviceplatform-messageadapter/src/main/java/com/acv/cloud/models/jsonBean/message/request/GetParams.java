package com.acv.cloud.models.jsonBean.message.request;


public class GetParams {
    private MessageRequest data;

    public void setData(MessageRequest data) {
        this.data = data;
    }

    public MessageRequest getData() {
        return data;
    }

    public GetParams(MessageRequest data) {
        this.data = data;
    }
}
