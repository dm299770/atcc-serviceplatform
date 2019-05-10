package com.acv.cloud.domain.body.req.message;


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
