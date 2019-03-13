package com.acv.cloud.models.carControl;

import com.acv.cloud.models.carControl.Vehicle;


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

    public VinRequestParameter(Vehicle data) {
        this.data = data;
    }
}
