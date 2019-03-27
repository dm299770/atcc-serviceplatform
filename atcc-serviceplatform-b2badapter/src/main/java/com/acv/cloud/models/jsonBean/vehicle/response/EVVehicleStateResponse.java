package com.acv.cloud.models.jsonBean.vehicle.response;

public class EVVehicleStateResponse {
    //续航里程
    private Float usablekm;
    //剩余电量
    private Float resbatterycap;

    public Float getUsablekm() {
        return usablekm;
    }

    public void setUsablekm(Float usablekm) {
        this.usablekm = usablekm;
    }

    public Float getResbatterycap() {
        return resbatterycap;
    }

    public void setResbatterycap(Float resbatterycap) {
        this.resbatterycap = resbatterycap;
    }

    public EVVehicleStateResponse(Float usablekm, Float resbatterycap) {
        this.usablekm = usablekm;
        this.resbatterycap = resbatterycap;
    }
}
