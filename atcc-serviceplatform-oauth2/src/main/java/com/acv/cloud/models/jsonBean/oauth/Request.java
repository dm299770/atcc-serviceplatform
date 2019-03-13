package com.acv.cloud.models.jsonBean.oauth;

import java.io.Serializable;
import java.util.Map;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
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

    protected Map<String, Object> data;

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request [version=" + version + ", businessId=" + businessId
                + ", serviceType=" + serviceType + ", accessToken="
                + accessToken + ", requestId=" + requestId + ", data=" + data
                + "]";
    }

}
