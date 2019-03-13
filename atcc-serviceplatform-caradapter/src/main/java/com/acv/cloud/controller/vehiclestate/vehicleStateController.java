package com.acv.cloud.controller.vehiclestate;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.remote.RequestParameters;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.DeleteDataRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.VehicleStateRequestParameter;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.WriteVehicleStateRequestParameter;
import com.acv.cloud.services.vehicleState.VehicleStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:远程寻车
 * @author:
 */
@RestController
@RequestMapping({"/vehiclestate"})
public class vehicleStateController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VehicleStateService vehicleStateService;
    @ResponseBody
    @RequestMapping(value = "vehiclestate")
    public Object finder(@RequestBody RequestParameters data) {
        JSONObject result=null;
        try {
            result = vehicleStateService.vehiclestate(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "deletedata")
    public Object deletedata(@RequestBody DeleteDataRequestParameter data) {
        JSONObject result=null;
        try {
            result = vehicleStateService.deletedata(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "EVVehicleState")
    public Object EVvehiclestate(@RequestBody VehicleStateRequestParameter data) {
        JSONObject result=null;
        try {
            result = vehicleStateService.EVvehiclestate(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "airConditionState")
    public Object airConditionState(@RequestBody VinRequestParameter data) {
        JSONObject result=null;
        try {
            result = vehicleStateService.airConditionState(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "ALLvehiclestate")
    public Object ALLvehiclestate(@RequestBody VinRequestParameter data) {
        JSONObject result=null;
        try {
            result = vehicleStateService.ALLvehiclestate(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "Writevehiclestate")
    public Object Writevehiclestate(@RequestBody WriteVehicleStateRequestParameter data) {
        JSONObject result=null;
        try {
            result = vehicleStateService.Writevehiclestate(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}


