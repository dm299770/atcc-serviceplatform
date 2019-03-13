package com.acv.cloud.controller.finder;

import com.acv.cloud.jsonBean.finder.request.SendToCarRequestParameter;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.finder.request.PathRequestParameter;
import com.acv.cloud.jsonBean.remote.RequestParameters;
import com.acv.cloud.services.finder.FinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description:远程寻车
 * @author:@guo.zj
 */
@RestController
@RequestMapping({"/find"})
public class FinderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FinderService finderService;
    @ResponseBody
    @RequestMapping(value = "finder")
    public Object finder(@RequestBody RequestParameters data) {
        JSONObject result=null;
        try {
            result = finderService.finder(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "sendToCar")
    public Object sendToCar(@RequestBody SendToCarRequestParameter data) {
        JSONObject result=null;
        try {
            result = finderService.sendToCar(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "path")
    public Object path(@RequestBody PathRequestParameter data) {
        JSONObject result=null;
        try {
            result = finderService.path(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}


