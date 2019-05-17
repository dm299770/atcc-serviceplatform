package com.acv.cloud.services.car;



import com.acv.cloud.domain.body.req.car.control.ControlRequestmeters;
import com.acv.cloud.dto.sys.UserInfo;
import com.alibaba.fastjson.JSONObject;


/**
 * @description:登录相关的方法
 * @author:@leo.
 */
public interface CarService {
    /**
     *车控统一接口
     *
     * @param vin车架号
     * @return 经纬度
     */
    JSONObject control(ControlRequestmeters request, UserInfo user);

}

