package com.acv.cloud.frame.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPropertiesConstants {

    @Value("${web.img-path}")
    private String photoPath;

    @Value("${server.host}")
    private String serverHost;
    @Value("${server.port}")
    private String serverPort;
    @Value("${sms.uri}")
    private String SmsUri;
    @Value("${sms.account}")
    private String SmsAccount;
    @Value("${sms.password}")
    private String SmsPassword;


    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getSmsUri() {
        return SmsUri;
    }

    public void setSmsUri(String smsUri) {
        SmsUri = smsUri;
    }

    public String getSmsAccount() {
        return SmsAccount;
    }

    public void setSmsAccount(String smsAccount) {
        SmsAccount = smsAccount;
    }

    public String getSmsPassword() {
        return SmsPassword;
    }

    public void setSmsPassword(String smsPassword) {
        SmsPassword = smsPassword;
    }
}
