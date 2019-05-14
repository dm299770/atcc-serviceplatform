package com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter;



import com.acv.cloud.models.car.CarVehicleState;

/**
 * 请求bean
 * @author GUO.ZJ
 */
public class VehicleStateRequestParameter {
    private CarVehicleState data;


    public CarVehicleState getData() {
        return data;
    }

    public void setData(CarVehicleState data) {
        this.data = data;
    }
}
