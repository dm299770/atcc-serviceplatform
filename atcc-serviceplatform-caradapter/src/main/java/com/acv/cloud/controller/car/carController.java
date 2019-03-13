package com.acv.cloud.controller.car;


import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.services.vehicleState.VehicleStateService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:车辆装态
 * @author:guo.zj
 */
@RestController
@RequestMapping({"/car"})
public class carController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VehicleStateService vehicleStateService;

    @ResponseBody
    @LoginRequired
    @RequestMapping(value = "status")
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

}


