package com.acv.cloud.controller.callback;

import com.acv.cloud.jsonBean.car.ControlRequestmeters;
import com.acv.cloud.jsonBean.remote.RemoteCallBackParameters;
import com.acv.cloud.services.callback.RemoteCallBackService;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:远程寻车
 * @author:@guo.zj
 *
 * 根据confluence格式 将车辆状态  车控  远程定位  历史电量查询 放入统一的controller
 */


@RestController
@RequestMapping({"/remotecallback"})
public class RemoteCallBackController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RemoteCallBackService remoteCallBackService;


    //车辆远程定位
    @ResponseBody
    @RequestMapping(value = "remotecallback")
    public Object remotecallback(@RequestBody RemoteCallBackParameters data) {
        JSONObject result=null;
        try {
            result = remoteCallBackService.remoteCallBack(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


}


