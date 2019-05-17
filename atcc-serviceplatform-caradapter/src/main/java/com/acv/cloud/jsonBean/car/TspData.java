package com.acv.cloud.jsonBean.car;

import com.acv.cloud.jsonBean.vehiclestate.TSP.TSPVehicleState;

public class TspData {

    private TSPVehicleState data;

    private String msg;

    private int status;


    public TSPVehicleState getData() {
        return data;
    }

    public void setData(TSPVehicleState data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
