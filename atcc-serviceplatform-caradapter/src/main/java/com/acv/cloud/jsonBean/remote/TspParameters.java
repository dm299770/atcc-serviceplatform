package com.acv.cloud.jsonBean.remote;

import com.acv.cloud.models.remotecallback.RemoteCallBack;

public class TspParameters {
    private RemoteCallBack data;

    public RemoteCallBack getData() {
        return data;
    }

    public void setData(RemoteCallBack data) {
        this.data = data;
    }
}
