package com.acv.cloud.controller.remote;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.finder.request.RequestParameterForCtrl;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.AirConditionRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.EVWindowRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.EVvehicleCtrlRequestParameter;
import com.acv.cloud.jsonBean.remote.remoteRequestParmmeter.VinRequestParameter;
import com.acv.cloud.services.remote.RemoteService;
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
 */

@RestController
@RequestMapping({"/remotecontrol"})
public class RemoteController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
        private RemoteService remoteService;
    @ResponseBody
    @RequestMapping(value = "remote")
    public Object remote(@RequestBody RequestParameterForCtrl data) {
        JSONObject result=null;
        try {
            //result = remoteService.remote(data);
        } catch (Exception e) {
            // TODO Auto-generated  catch block
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @description:空调开关
     * @author:@guo.zj
     */
    @ResponseBody
    @RequestMapping(value = "airconditionerturn")
    public Object airconditionertrun(@RequestBody AirConditionRequestParameter data) {
        JSONObject result=null;
        try {
            //result = remoteService.airconditionerturn(data);
        } catch (Exception e) {
            // TODO Auto-generated  catch block
            e.printStackTrace();
        }
        return result;
    }





}


