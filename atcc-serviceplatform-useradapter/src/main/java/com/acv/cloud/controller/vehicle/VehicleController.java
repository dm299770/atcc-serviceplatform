package com.acv.cloud.controller.vehicle;

import com.acv.cloud.jsonBean.user.bindCar.requetJson.BindCarParams;
import com.acv.cloud.jsonBean.user.setDefaultCar.requestJson.SetDefaultCarParams;
import com.acv.cloud.jsonBean.user.verifyCode.requestJson.Attributes;
import com.acv.cloud.jsonBean.user.verifyCode.requestJson.Data;
import com.acv.cloud.jsonBean.user.verifyCode.requestJson.VerifyCodeParams;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.util.JsonUtil;
import com.acv.cloud.jsonBean.vehicle.VehicleInfoData;
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
import java.util.Optional;

/**
 * @description:车辆管理
 * @author:@leo.
 */

@RestController
@RequestMapping({"/user"})
public class VehicleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    VehicleService vehicleServiceImpl;

    /**
     * 获取当前登录用户下所有绑定车辆
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/cars/v1")
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
    @RequestMapping(value = "/bindCar/v1")
    public Object bindVehicle(@CurrentUser UserInfo user, @RequestBody BindCarParams bindCarParams) {
        logger.info("VehicleController BindCarParams:"+bindCarParams.toString());

        String vin = Optional.ofNullable(bindCarParams)
                .map(BindCarParams::getData)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Attributes::getVin).orElse(null);

        String engineNum = Optional.ofNullable(bindCarParams)
                .map(BindCarParams::getData)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Attributes::getEngineNum).orElse(null);

        String plateNum = Optional.ofNullable(bindCarParams)
                .map(BindCarParams::getData)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Attributes::getPlateNum).orElse(null);


        String lastSixPhoneNum = Optional.ofNullable(bindCarParams)
                .map(BindCarParams::getData)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.bindCar.requetJson.Attributes::getLastSixPhoneNum).orElse(null);


        JSONObject jsonObject = null;
        Date now = new Date();
        if (user != null) {
            if (vin != null
                    && engineNum!=null
                    && plateNum !=null
                    && lastSixPhoneNum !=null ) {
                TrUserVin trUserVin = new TrUserVin();
                trUserVin.setVin(vin);
                trUserVin.setEngineNum(engineNum);
                trUserVin.setPlateNum(plateNum);
                trUserVin.setLastSixPhoneNum(lastSixPhoneNum);
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
    @RequestMapping(value = "/unbindCar/v1")
    public Object unbindVehicle(@CurrentUser UserInfo user, @RequestBody SetDefaultCarParams setDefaultCarParams) {
        logger.info("VehicleController SetDefaultCarParams:"+setDefaultCarParams.toString());

        String vin = Optional.ofNullable(setDefaultCarParams)
                .map(SetDefaultCarParams::getData)
                .map(com.acv.cloud.jsonBean.user.setDefaultCar.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.setDefaultCar.requestJson.Attributes::getVin).orElse(null);
        JSONObject jsonObject = null;
        if (vin != null) {

            jsonObject = vehicleServiceImpl.updateVehicle(user.getUserId(), vin);



        } else {
                jsonObject.put(AppResultConstants.MSG, AppResultConstants.Paramer_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }


        return jsonObject;
    }


    /**
     * 设置默认车辆
     */

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/setDefaultCar/v1")
    public Object setDefaultVehicle(@CurrentUser UserInfo user, @RequestBody SetDefaultCarParams setDefaultCarParams) {

        logger.info("VehicleController SetDefaultCarParams:"+setDefaultCarParams.toString());

        String vin = Optional.ofNullable(setDefaultCarParams)
                .map(SetDefaultCarParams::getData)
                .map(com.acv.cloud.jsonBean.user.setDefaultCar.requestJson.Data::getAttributes)
                .map(com.acv.cloud.jsonBean.user.setDefaultCar.requestJson.Attributes::getVin).orElse(null);


        JSONObject jsonObject = null;
        if (vin != null) {
            jsonObject = vehicleServiceImpl.setDefaultVehicle(user.getUserId(), vin);

        } else {
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.Paramer_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }


        return jsonObject;
    }


}
