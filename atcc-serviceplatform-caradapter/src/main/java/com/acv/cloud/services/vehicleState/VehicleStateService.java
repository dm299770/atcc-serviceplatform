package com.acv.cloud.services.vehicleState;


import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.alibaba.fastjson.JSONObject;


/**
 * @description:车辆状态
 * @author:@guo.zj
 */
public interface VehicleStateService {
    /**
     * 车辆状态
     *
     * @param vin车架号 操作类型
     * @return 成功失败
     */
    JSONObject EVvehiclestate(String vin,int mode);

    /**
     * 空调状态
     *
     * @param vin车架号 操作类型
     * @return 空调状态
     */
    JSONObject airConditionState(VinRequestParameter request);

}

