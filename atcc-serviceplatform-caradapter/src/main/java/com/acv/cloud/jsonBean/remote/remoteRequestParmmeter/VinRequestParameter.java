package com.acv.cloud.jsonBean.remote.remoteRequestParmmeter;


import com.acv.cloud.models.remote.Vehicle;

/**
 * 请求bean
 * @author leo
 */
public class VinRequestParameter {
    private Vehicle data;


    public Vehicle getData() {
        return data;
    }

    public void setData(Vehicle data) {
        this.data = data;
    }
}
