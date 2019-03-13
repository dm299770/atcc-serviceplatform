package com.acv.cloud.jsonBean.finder.request;

import com.acv.cloud.jsonBean.remote.remotemodel.VehicleCtrl;

/**
 * 请求bean
 * @author leo
 */
public class RequestParameterForCtrl {
    private VehicleCtrl data;


    public VehicleCtrl getData() {
        return data;
    }

    public void setData(VehicleCtrl data) {
        this.data = data;
    }
}
