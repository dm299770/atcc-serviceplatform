package com.acv.cloud.services.remote;


import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.jsonBean.remote.parameter.AirConditionRequestParameter;
import com.acv.cloud.jsonBean.remote.parameter.EVvehicleCtrlRequestParameter;
import com.alibaba.fastjson.JSONObject;

/**
 * @description:登录相关的方法
 * @author:@leo.
 */
public interface RemoteService {
    /**
     * 空调开关
     *
     * @param vin车架号 操作类型
     * @return 成功失败
     */
    JSONObject airconditionerturn(AirConditionRequestParameter request, int onoff, UserInfo user);


    /**
     * 启车 闪灯鸣笛 后备箱 车窗 天窗 门锁
     * @param vin车架号 操作类型
     * @return 成功失败
     */
    JSONObject tspControl(EVvehicleCtrlRequestParameter request, String cmdid, UserInfo user);

}

