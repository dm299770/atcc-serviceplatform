package com.acv.cloud.services.account;

import com.acv.cloud.models.jsonBean.vehicle.request.VehicleStateRequestParameter;
import com.alibaba.fastjson.JSONObject;

public interface AccountService {
    /**
     * 用户充电扣款
     *
     * @param user_id 用户主键
     * @param money   充电扣费
     * @return
     */
    JSONObject deduct(String user_id, String money, String comment) throws Exception;

    /**
     * @param user_id 用户主键
     * @return
     */
    JSONObject selAll(String user_id);

    /**
     * @param user_id 用户主键
     * @return
     */
    JSONObject selBalance(String user_id);

    /**
     * 车辆状态
     *
     * @param data
     * @return
     */
    JSONObject vehicleState(VehicleStateRequestParameter data);

}
