package com.acv.cloud.controller.device;

import com.acv.cloud.services.device.DeviceSerivce;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/device"})
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DeviceSerivce deviceSerivce;

    @RequestMapping("/getDeviceNoByCache/{userid}/{deviceType}")
    @ResponseBody
    public Object getDeviceNo(@PathVariable("userid")String userid , @PathVariable("deviceType") String deviceType){

        return deviceSerivce.getDeviceNoFromCaChe(userid,deviceType);
    }

}
