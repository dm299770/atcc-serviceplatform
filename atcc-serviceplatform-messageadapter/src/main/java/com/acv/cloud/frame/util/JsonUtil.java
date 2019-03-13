package com.acv.cloud.frame.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonUtil {
    public static JSONObject EraseNull(JSONObject source){
        JSONObject target = new JSONObject();
        ValueFilter valueFilter = new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {
                if (value == null)
                    return "";
                return value;
            } };
        String result = JSONObject.toJSONString(source,valueFilter);
        target = JSON.parseObject(result);
        return target;
    }
}
