package com.acv.cloud.feign;

import com.acv.cloud.models.jsonBean.vehicle.request.VehicleStateRequestParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-caradapter")
public interface VehicleStateFeign {

    @ResponseBody
    @RequestMapping(value = "/vehiclestate/EVVehicleState",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object EVvehiclestate(@RequestBody VehicleStateRequestParameter data);

}