package com.acv.cloud.models.jsonBean.login.responseJson;

public class LoginData {
    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginData(String accessToken) {
        this.accessToken = accessToken;
    }
}
