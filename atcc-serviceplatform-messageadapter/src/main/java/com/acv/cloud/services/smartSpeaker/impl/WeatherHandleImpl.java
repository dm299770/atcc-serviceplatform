//package com.acv.cloud.services.smartSpeaker.impl;
//
//import com.acv.cloud.services.smartSpeaker.WeatherHandle;
//import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
//import com.alibaba.da.coin.ide.spi.standard.TaskResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class WeatherHandleImpl implements WeatherHandle {
//    private static final Logger logger = LoggerFactory.getLogger(WeatherHandleImpl.class);
//
//    @Override
//    public TaskResult execute(TaskQuery taskQuery) {
//        logger.info("WeatherHandleImpl execute...");
//
////        //从请求中获取意图参数以及参数值
////        Map<String, String> paramMap = taskQuery.getSlotEntities().stream().collect(
////                Collectors.toMap(
////                        slotItem -> slotItem.getIntentParameterName(),//map的key,意图参数名
////                        slotItem -> slotItem.getStandardValue()));//map的value,原始solt归一化后的值
//
////        logger.info("paramMap ：" + paramMap.toString());
//        //如果意图是询问空气质量，则执行空气质量逻辑
//        if (taskQuery.getIntentName().equals("空气质量")) {
////            return aqiQuery(taskQuery, paramMap);
//            //如果意图是询问天气情况，则执行天气查询逻辑
//            return null;
//        } else if (taskQuery.getIntentName().equals("天气查询")) {
////            return baseQuery(taskQuery, paramMap);
//            return null;
//        } else {
//            return null;
//        }
//    }
//
//    private TaskResult baseQuery(TaskQuery taskQuery, Map<String, String> paramMap) {
////        TaskResult result = new TaskResult();
////        try {
//////            //请求服务并填充回复语句
////            List<BasicNameValuePair> params = new ArrayList<>();
////            params.add(new BasicNameValuePair("areaName", paramMap.get("city")));
////            params.add(new BasicNameValuePair("date", DateUtil.getStartDate(paramMap.get("time"))));
////            String executeBody = httpGet(params);
////            String weather = getWeather(executeBody);
////
////            Map<String, String> properties = new HashMap<String, String>();
////            properties.put("city", paramMap.get("city"));
////            properties.put("time", paramMap.get("time"));
////            properties.put("weather", weather);
////            properties.put("temp_low", getTempLow(executeBody));
////            properties.put("temp_high", getTempHigh(executeBody));
////            properties.put("wind_direct", getWindDirect(executeBody));
////            properties.put("power", getPower(executeBody));
////            if (weather == null) {
////                result.setReply("对不起，我现在只支持查询最近4天的天气");
////            } else {
////                result.setReply(TemplateFillUtil.fillTemplate(
////                                "@{city} @{time}天气 @{weather}，温度@{temp_low}到@{temp_high}度，@{wind_direct}@{power}",
////                                properties));
////            }
////            result.setExecuteCode(ExecuteCode.SUCCESS);
////            result.setResultType(ResultType.RESULT);
////        } catch (Exception e) {
////            logger.info("query exception", e);
////            result.setExecuteCode(ExecuteCode.EXECUTE_ERROR);
////        }
//        return null;
//    }
//}