package com.acv.cloud.controller.test;

import com.acv.cloud.jsonBean.remote.RemoteCallBackParameters;
import com.acv.cloud.jsonBean.remote.tspremotemodel.RemoteTspVehicle;
import com.acv.cloud.services.callback.RemoteCallBackService;
import com.acv.cloud.services.test.HttpAPITestService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author:@leo.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private static String PAGE_FILE_NAME = "test/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpAPITestService httpAPIService;

    @Autowired
    private RemoteCallBackService remoteCallBackService;

}
