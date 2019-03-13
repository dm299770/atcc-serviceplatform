package com.acv.cloud.jsonBean.message;

/**
 * 消息中心查询消息
 * @author guo.zj   返回给手机
 */
public class ReturnJsonMessage {

    private ReturnMessage[] message;


    public ReturnMessage[] getMessage() {
        return message;
    }

    public void setMessage(ReturnMessage[] message) {
        this.message = message;
    }
}
