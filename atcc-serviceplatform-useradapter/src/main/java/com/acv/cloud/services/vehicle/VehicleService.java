package com.acv.cloud.services.vehicle;


import com.acv.cloud.domain.dto.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.models.vehicle.TrUserVin;

import java.util.List;

/**
 * 车辆信息管理
 *
 * @author leo
 */
public interface VehicleService {
    /**
     * 查找用户全部绑定车辆
     */
    JSONObject findBindVehicleByUser(String userId);

    /**
     * 绑定车辆
     */
    JSONObject saveVehicle(TrUserVin trUserVin);

    /**
     * 解绑车辆
     */
    JSONObject updateVehicle(UserInfo userInfo, String vin, String code);

    /**
     * 设置默认车辆
     */
    JSONObject setDefaultVehicle(String userId, String vin);

    /**
     * vin号是否存在
    */
    Boolean existVin(String vin);


}
