package com.acv.cloud.frame.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class HttpUtil {

    protected static final Logger logger = LoggerFactory.getLogger("neusoft");

    /**
     * 业务请求转发
     *
     * @param url    请求转发地址
     * @param params json数据
     * @return 请求响应结果
     * @throws Exception
     */
    public static String getResponse(String url, String params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else {
                logger.error("请求返回--状态:{}, 请求转发地址:{}", state, url);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return "";
    }

    public static void main(String[] args) {
        String url = "http://localhost:8010/neusoft-oauth2-server/oauth/openapi";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("version", "0100");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("version", "0100");
        jsonObj.put("businessId", "Amap");
        jsonObj.put("serviceType", "login");
        jsonObj.put("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkJEQ0hSMDEifQ.eyJzY3AiOiJzY3AwMTAwMSIsImV4cCI6IjMwMDE1MjQ3OTYzNTgiLCJzdWIiOiIzMDA3NCIsInRudCI6IkNoZXJ5X0NOIiwiYXVkIjoiQmFpZHUiLCJpc3MiOiJDSFJDTlRTUDAxVFVJMyIsInN0eXAiOiJUMSIsInR5cCI6IkFUIiwidmVyIjoiMSIsImlhdCI6IjE1MjQ3OTYzNTgifQ.Sni-wBq743I7yEGuQBGUKDSVhycL_VQhtlAkSC6FsdQ");
        jsonObj.put("requestId", "aaasssddffgghh");
        jsonObj.put("data", map);
        try {
            System.out.println(getResponse(url, jsonObj.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  

