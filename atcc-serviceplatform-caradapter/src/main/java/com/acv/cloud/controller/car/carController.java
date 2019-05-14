package com.acv.cloud.controller.car;

import com.acv.cloud.domain.body.req.car.control.ControlRequestmeters;
import com.acv.cloud.domain.model.car.control.AirCondition;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.jsonBean.vehiclestate.vehiclestateRequestParameter.VehicleStateRequestParameter;
import com.acv.cloud.services.car.CarService;
import com.acv.cloud.services.vehicleState.VehicleStateService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping({"/car"})
public class CarController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CarService carService;
    @Autowired
    private VehicleStateService vehicleStateService;
    //车辆远程控制
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "control/v1")
    public Object control(@CurrentUser UserInfo user, @RequestBody ControlRequestmeters data) {
        logger.info("CarController VehiclestateParams:" +data.toString());
        String vin = Optional.ofNullable(data)
                .map(ControlRequestmeters::getData)
                .map(com.acv.cloud.domain.body.req.car.control.Data::getAttributes)
                .map(com.acv.cloud.domain.model.car.control.Control::getVin).orElse(null);

        String cmdid = Optional.ofNullable(data)
                .map(ControlRequestmeters::getData)
                .map(com.acv.cloud.domain.body.req.car.control.Data::getAttributes)
                .map(com.acv.cloud.domain.model.car.control.Control::getCmdid).orElse(null);

        int smsflag= Optional.ofNullable(data)
                .map(ControlRequestmeters::getData)
                .map(com.acv.cloud.domain.body.req.car.control.Data::getAttributes)
                .map(com.acv.cloud.domain.model.car.control.Control::getSmsflag).orElse(0);

        int onoff= Optional.ofNullable(data)
                .map(ControlRequestmeters::getData)
                .map(com.acv.cloud.domain.body.req.car.control.Data::getAttributes)
                .map(com.acv.cloud.domain.model.car.control.Control::getOnoff).orElse(0);

        AirCondition airCondition= Optional.ofNullable(data)
                .map(ControlRequestmeters::getData)
                .map(com.acv.cloud.domain.body.req.car.control.Data::getAttributes)
                .map(com.acv.cloud.domain.model.car.control.Control::getAirconditioner).orElse(null);
        JSONObject result=null;
        try {
            result = carService.control(data,user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    //车辆远程状态
    @ResponseBody
    @RequestMapping(value = "status/v1")
    public Object status(@RequestBody VehicleStateRequestParameter data) {
        logger.info("CarController VehiclestateParams:" +data.toString());
        String vin = Optional.ofNullable(data)
                .map(VehicleStateRequestParameter::getData)
                .map(com.acv.cloud.models.car.CarVehicleState::getAttributes)
                .map(com.acv.cloud.jsonBean.vehiclestate.vehiclestatemodel.RequestVehicleState::getVin).orElse(null);

        int mode = Optional.ofNullable(data)
                .map(VehicleStateRequestParameter::getData)
                .map(com.acv.cloud.models.car.CarVehicleState::getAttributes)
                .map(com.acv.cloud.jsonBean.vehiclestate.vehiclestatemodel.RequestVehicleState::getMode).orElse(0);
        JSONObject result=null;
        try {
            result = vehicleStateService.EVvehiclestate(vin,mode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
