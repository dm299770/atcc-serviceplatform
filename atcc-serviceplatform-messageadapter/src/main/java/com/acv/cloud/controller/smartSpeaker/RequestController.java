//package com.acv.cloud.controller.smartSpeaker;
//
//import com.acv.cloud.services.smartSpeaker.WeatherHandle;
//import com.alibaba.da.coin.ide.spi.standard.ResultModel;
//import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
//import com.alibaba.da.coin.ide.spi.standard.TaskResult;
//import com.alibaba.da.coin.ide.spi.trans.MetaFormat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
////    MetaFormat.parseToQuery( String requestMetaData);
////    requestMetaData是框架调用执行逻辑服务url时传输过去的请求数据post body
//
////    ResultModel<TaskResult> result //执行结果对象
//
///**
// * 以天气查询为例的服务提供者的demo示例
// */
////请求接收者，将最终结果返回
//@Controller
//public class RequestController {
//
//    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
//
//    @Autowired
//    private WeatherHandle weatherHandle;
//
//    /**
//     * skill开发者提供的技能执行路径地址，请求方式为POST请求
//     *
//     * @param taskQuery
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/skill/weather", method = RequestMethod.POST)
//    public ResultModel<TaskResult> getResponse(@RequestBody String taskQuery) {
//
//        /**
//         * 将开发者平台识别到的语义理解的结果（json字符串格式）转换成TaskQuery
//         */
//        logger.info("TaskQuery:{}", taskQuery.toString());
//
//        TaskQuery query = MetaFormat.parseToQuery(taskQuery);//框架调用执行逻辑服务url时传输过去的请求数据post body
//
//        /**
//         * 构建服务返回结果
//         */
//        ResultModel<TaskResult> resultModel = new ResultModel<TaskResult>();
//        try {
//            /**
//             * 调用天气服务执行并构建回复内容
//             */
//            TaskResult result = weatherHandle.execute(query);
//            resultModel.setReturnCode("0"); //0，标识成功
//            resultModel.setReturnValue(result);//意图理解后的执行结果
//        } catch (Exception e) {
//            resultModel.setReturnCode("-1");
//            resultModel.setReturnErrorSolution(e.getMessage());
//        }
//        /**
//         * 直接返回ResultModel<TaskResult>对象就ok
//         */
//        return resultModel;
//    }
//
//}
//
