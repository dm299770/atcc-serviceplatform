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

}


