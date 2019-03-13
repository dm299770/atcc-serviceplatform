package com.acv.cloud.feign.caradapter;


import com.acv.cloud.models.carControl.VinRequestParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "atcc-serviceplatform-caradapter")
public interface CarFegin {

    //车辆控制服务
    @ResponseBody
    @RequestMapping(value = "/car/status",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getVehicleState(@RequestBody VinRequestParameter data);

}
