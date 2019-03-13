package com.acv.cloud.services.vehicleState;


import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.remote.RequestParameters;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.DeleteDataRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.VehicleStateRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.WriteVehicleStateRequestParameter;


/**
 * @description:车辆状态
 * @author:@guo.zj
 */
public interface VehicleStateService {
    /**
     * 车辆状态
     *
     * @param vin车架号
     * @return 车辆状态
     */
    JSONObject vehiclestate(RequestParameters request);


    /**
     * 个人数据处理
     *
     * @param vin车架号
     * @return 重置状态
     */
    JSONObject deletedata(DeleteDataRequestParameter request);

    /**
     * 车辆状态
     *
     * @param vin车架号 操作类型
     * @return 成功失败
     */
    JSONObject EVvehiclestate(VehicleStateRequestParameter request);

    /**
     * 空调状态
     *
     * @param vin车架号 操作类型
     * @return 空调状态
     */
    JSONObject airConditionState(VinRequestParameter request);

    /**
     * 车辆状态
     *
     * @param vin车架号 操作类型
     * @return 空调状态
     */
    JSONObject ALLvehiclestate(VinRequestParameter request);


    /**
     * 车辆状态写入
     *
     * @param vin车架号 操作类型
     * @return 空调状态
     */
    JSONObject Writevehiclestate(WriteVehicleStateRequestParameter request);
}

