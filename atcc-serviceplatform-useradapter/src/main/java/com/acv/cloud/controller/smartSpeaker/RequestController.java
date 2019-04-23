package com.acv.cloud.controller.smartSpeaker;

//import com.alibaba.da.coin.ide.spi.standard.ResultModel;
//import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
//import com.alibaba.da.coin.ide.spi.standard.TaskResult;
//import com.alibaba.da.coin.ide.spi.trans.MetaFormat;

import com.alibaba.fastjson.JSONObject;


import com.acv.cloud.frame.constants.DataFormat;
import com.acv.cloud.jsonBean.smartSpeaker.mi.MiTaskRequest;
import com.acv.cloud.services.smartSpeaker.impl.MiAiSmallEvHandeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//import com.google.gson.JsonObject;


/**
 *以天气查询为例的服务提供者的demo示例
 */
//请求接收者，将最终结果返回
@Controller
@RequestMapping({"/smartSpeaker"})
public class RequestController {


    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    MiAiSmallEvHandeImpl smallEvHande;

    @RequestMapping("/")
    @ResponseBody
    String smallev() {

        return "Hello smallev!";

    }



    @RequestMapping(value = "/soc1",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object soc1(HttpServletRequest request, @RequestBody JSONObject jsonParam){
        logger.info("------------------------------------>> soc1 action access");
        //logger.info("client ip :"+ HttpUtils.getIpAddr(request));
        logger.info("jsonParam :"+jsonParam.toJSONString());

        /**解析智能音箱请求*/
        MiTaskRequest miTaskRequest = DataFormat.parseToQuery(jsonParam);
        JSONObject jsonObject = smallEvHande.execute(miTaskRequest);
        return jsonObject;
    }

//    @RequestMapping(value = "carmodel")
//    @ResponseBody
//    public Object carmodel(HttpServletRequest request , String carModelID){
//        logger.info("------------------------------------>> carmodel action access");
//        logger.info("client ip :"+ HttpUtils.getIpAddr(request));
//
//        JSONObject jsonObject = new JSONObject();
//        Random r = new Random();
//        int soc = r.nextInt(101);
//
//
//        String json = "{\n" +
//                "\"status\": 1, \"soc\": "+soc+"\n" +
//                "}\n";
//        return JSON.parseObject(json);
//    }



}
