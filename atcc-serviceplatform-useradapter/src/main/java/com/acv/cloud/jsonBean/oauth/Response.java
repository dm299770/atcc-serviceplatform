package com.acv.cloud.jsonBean.oauth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response implements Serializable {

    private static final long serialVersionUID = 2065208980006141337L;

    /**
     * 协议版本
     **/
    protected String version = "";
    /**
     * 业务ID
     **/
    protected String businessId = "";
    /**
     * 服务类型
     **/
    protected String serviceType = "";
    /**
     * 用户令牌
     **/
    protected String accessToken = "";
    /**
     * 请求ID
     **/
    protected String requestId = "";
    /**
     * 应答返回码
     **/
    protected String resultCode = "";

    protected Map<String, Object> data = new HashMap<String, Object>();

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "Response [version=" + version + ", businessId=" + businessId
                + ", serviceType=" + serviceType + ", accessToken="
                + accessToken + ", requestId=" + requestId + ", resultCode="
                + resultCode + ", data=" + data + "]";
    }
}
	
