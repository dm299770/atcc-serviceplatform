package com.acv.cloud.frame.constants.app;

import com.acv.cloud.frame.constants.AppResultConstants;

/**
 * @Author: leo
 * @Date: 2019/4/26 17:54
 */
public class VehicleAppResultConstants extends AppResultConstants {

    public static final Integer VIN_EXIST = 3002 ; //车辆已绑定

    public static final Integer VIN_ISNOT_EXIST = 3003; // vin不存在

    public static final Integer UNBIND_ISNOT_DEFAULT = 3004 ;



    public static final String VIN_EXIST_MSG = "车辆已绑定";

    public static final String FIND_VEHICLE_SUCCESS = "获取车辆信息成功";

    public static final String SET_DEFAULT_SUCCESS = "设置默认车辆成功";

    public static final String VIN_ISNOT_EXIST_MSG = "车辆信息不存在";

    public static final String UNBIND_ISNOT_DEFAULT_MSG = "多辆绑定车辆不能解绑默认车辆";

    public static final String UNBIND_VEHICLE_SUCCESS = "解绑车辆信息成功";




}
