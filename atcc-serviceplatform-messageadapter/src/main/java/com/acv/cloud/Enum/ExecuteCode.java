package com.acv.cloud.Enum;

public enum ExecuteCode {

    SUCCESS,//代表执行成功
    PARAMS_ERROR,//代表接收到的请求参数出错
    EXECUTE_ERROR,//代表自身代码有异常
    REPLY_ERROR;//代表回复结果生成出错

    private ExecuteCode() {
    }
}