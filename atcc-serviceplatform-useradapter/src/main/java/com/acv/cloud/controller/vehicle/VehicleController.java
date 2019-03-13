package com.acv.cloud.controller.vehicle;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.util.JsonUtil;
import com.acv.cloud.models.jsonBean.vehicle.VehicleInfoData;
import com.acv.cloud.models.vehicle.TrUserVin;
import com.acv.cloud.services.vehicle.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description:车辆管理
 * @author:@leo.
 */

@RestController
@RequestMapping({"/vehicle"})
public class VehicleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    VehicleService vehicleServiceImpl;

    /**
     * 获取当前登录用户下所有绑定车辆
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/getVehicleList")
    public Object getVehicleList(@CurrentUser UserInfo user) {
        JSONObject jsonObject = null;
        if (user != null) {
            jsonObject = vehicleServiceImpl.findBindVehicleByUser(user.getUserId());

        } else {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.LOGIN_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }
        jsonObject = JsonUtil.EraseNull(jsonObject);
        return jsonObject;
    }

    /***
     *绑定车辆
     */

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/bindVehicle")
    public Object bindVehicle(@CurrentUser UserInfo user, @RequestBody VehicleInfoData vehicleInfoData) {
        JSONObject jsonObject = null;
        Date now = new Date();
        if (user != null) {
            if (vehicleInfoData != null) {
                TrUserVin trUserVin = new TrUserVin();
                BeanUtils.copyProperties(vehicleInfoData, trUserVin);
                trUserVin.setUserId(user.getUserId());//存入userid
                trUserVin.setBindingDate(now);//绑定日期
                trUserVin.setCreateDate(now);//创建日期
                trUserVin.setIsEffctive(1);

                jsonObject = vehicleServiceImpl.saveVehicle(trUserVin);
            } else {
                jsonObject.put(AppResultConstants.MSG, AppResultConstants.Paramer_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }

        } else {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.LOGIN_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }
        return jsonObject;
    }

    /**
     * 解绑车辆
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/unbindVehicle")
    public Object unbindVehicle(@CurrentUser UserInfo user, @RequestBody VehicleInfoData vehicleInfoData) {

        JSONObject jsonObject = null;
        if (user != null) {
            if (vehicleInfoData != null && !"".equals(vehicleInfoData.getVin())) {
                String plateNum = vehicleInfoData.getPlateNum();
                String vin = vehicleInfoData.getVin();
                jsonObject = vehicleServiceImpl.updateVehicle(user.getUserId(), vin);
            } else {
                jsonObject.put(AppResultConstants.MSG, AppResultConstants.Paramer_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }

        } else {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.LOGIN_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }

        return jsonObject;
    }


    /**
     * 设置默认车辆
     */

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/setDefaultVehicle")
    public Object setDefaultVehicle(@CurrentUser UserInfo user, @RequestBody VehicleInfoData vehicleInfoData) {

        JSONObject jsonObject = null;
        if (user != null) {
            if (vehicleInfoData != null && !vehicleInfoData.getPlateNum().equals("")) {
                String plateNum = vehicleInfoData.getPlateNum();
                String vin = vehicleInfoData.getVin();
                jsonObject = vehicleServiceImpl.setDefaultVehicle(user.getUserId(), vin);
            } else {
                jsonObject.put(AppResultConstants.MSG, AppResultConstants.Paramer_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            }

        } else {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.LOGIN_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }


        return jsonObject;
    }


}
