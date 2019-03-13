package com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter;
import com.acv.cloud.models.vehiclestate.ALLVehicleState;

/**
 * 请求bean
 * @author GUO.ZJ
 */
public class WriteVehicleStateRequestParameter {
    private ALLVehicleState data;

    public ALLVehicleState getData() {
        return data;
    }

    public void setData(ALLVehicleState data) {
        this.data = data;
    }
}
