package com.acv.cloud.services.smartSpeaker.impl;

//import com.alibaba.da.coin.ide.spi.meta.ExecuteCode;
//import com.alibaba.da.coin.ide.spi.meta.ResultType;
//import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
//import com.alibaba.da.coin.ide.spi.standard.TaskResult;

import com.acv.cloud.dto.sys.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.models.jsonBean.smartSpeaker.TaskRequest;
import com.acv.cloud.services.smartSpeaker.SmallEvHande;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import com.google.gson.JsonObject;

@Component
public class SmallEvHandelimpl implements SmallEvHande {
    private static final Logger logger = LoggerFactory.getLogger(SmallEvHandelimpl.class);
//    @Override
//    public TaskResult execute(TaskQuery taskQuery){
//        //从请求中获取意图参数以及参数值
//        Map<String, String> paramMap = null;
//        if(taskQuery!=null){
//            taskQuery
//                    .getSlotEntities()
//                    .stream()
//                    .collect(
//                            Collectors.toMap(slotItem -> slotItem.getIntentParameterName(),
//                                    slotItem -> slotItem.getStandardValue()));
//            //logger.info("paramMap ：" + paramMap.toString());
//        }
//
//        return baseQuery(taskQuery,paramMap);
//    }



//    private TaskResult baseQuery(TaskQuery taskQuery, Map<String, String> paramMap) {
//        TaskResult result = new TaskResult();
//        result.setReply("我还有百分之三十的电");
//        result.setExecuteCode(ExecuteCode.SUCCESS);
//        result.setResultType(ResultType.RESULT);
//        try {
//            //请求服务并填充回复语句
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("areaName", paramMap.get("city")));
//            params.add(new BasicNameValuePair("date", DateUtil.getStartDate(paramMap.get("time"))));
//            String executeBody = httpGet(params);
//            String weather = getWeather(executeBody);
//            Map<String, String> properties = new HashMap<String, String>();
//
//            properties.put("city", paramMap.get("city"));
//            properties.put("time", paramMap.get("time"));
//            properties.put("weather", weather);
//            properties.put("temp_low", getTempLow(executeBody));
//            properties.put("temp_high", getTempHigh(executeBody));
//            properties.put("wind_direct", getWindDirect(executeBody));
//            properties.put("power", getPower(executeBody));
//            if (weather == null) {
//                result.setReply("对不起，我现在只支持查询最近4天的天气");
//            } else {
//                result.setReply(TemplateFillUtil
//                        .fillTemplate(
//                                "@{city} @{time}天气 @{weather}，温度@{temp_low}到@{temp_high}度，@{wind_direct}@{power}",
//                                properties));
//            }
//
//            result.setExecuteCode(ExecuteCode.SUCCESS);
//            result.setResultType(ResultType.RESULT);
//        } catch (Exception e) {
//            logger.info("query exception", e);
//            result.setExecuteCode(ExecuteCode.EXECUTE_ERROR);
//        }
//
//        return result;
//    }


    @Override
    public JSONObject execute(UserInfo userInfo , TaskRequest taskRequest) {
        return null;
    }
}
