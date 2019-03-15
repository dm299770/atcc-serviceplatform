package com.acv.cloud.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.OauthConstants;
import com.acv.cloud.frame.util.HttpUtil;
import com.acv.cloud.frame.util.JwtTokenUtil;
import com.acv.cloud.frame.util.ResponseUtil;
import com.acv.cloud.jsonBean.oauth.Request;
import com.acv.cloud.jsonBean.oauth.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Pattern;


public class Handler {

    public static final String RC_SUCCESS = "0200";// 服务器处理成功
    public static final String RC_HTTP_OVERTIME = "0201";// 服务器处理超时
    public static final String RC_SERVER_CAPACITY_BOTTLENECK = "0206";// 服务器处理能力达到极限，无法处理请求
    public static final String RC_ARGUMENT_ILLEGAL = "0400";// 请求协议体不符合协议规范-协议体长度不符合规范或是协议中必添项未填写
    public static final String RC_ARGUMENT_ILLEGAL_FORMAT = "0401";// 请求协议体不符合协议规范-请求数据值的格式错误
    public static final String RC_FUNCTIONS_ILLEGAL = "0403";// 用户服务已停用，无法使用服务
    public static final String RC_GID_INVALID = "0406";// globalId无效
    public static final String RC_SERVER_ERROR = "0500";// 服务器内部错误
    public static final String RC_DATABASE_TIMEOUT = "0501";// 服务器链接数据库失败

    protected static final Logger logger = LoggerFactory.getLogger("neusoft");

    private static String openapi_url;//业务转发url
    private static String openapi_scope;//业务权限
    protected String releaseCollection;

    public Object handle(Request req, Response res) throws Exception {
        String accessToken = req.getAccessToken();
        //验证version
        if (!"0100".equals(req.getVersion())) {
            logger.error("无效的Version:{}", req.getVersion());
            res.setResultCode("0400");
            return res;
        }
        //验证servicetype
        if ("".equals(req.getServiceType())) {
            logger.error("无效的ServiceType:{}", req.getServiceType());
            res.setResultCode("0400");
            return res;
        }
        String st = req.getServiceType();
        if (!Pattern.matches(releaseCollection, st)) {
            //token判空
            if ("".equals(req.getAccessToken())) {
                logger.error("无效的AccessToken:{}", req.getAccessToken());
                res.setResultCode("0400");
                return res;
            }
            //验证token
            if (JwtTokenUtil.checkAccessToken(accessToken)) {
                //token过期
                JSONObject json = ResponseUtil.getJson(req.getBusinessId(), req.getServiceType(), OauthConstants.INVALID_ACCESS_TOKEN);
                return json;
            }
            if (!checkScope(accessToken, req.getServiceType())) {
                logger.error("无效的ServiceType:{}", req.getServiceType());
                res.setResultCode("0400");
                return res;
            }
        }
        String url = getRequestUrl(req.getBusinessId());
        String params = getRequestParams(req);
        return JSONObject.parseObject(HttpUtil.getResponse(url, params));
    }

    //获取转发url
    private static String getRequestUrl(String businessId) {
        String[] urls = openapi_url.split(";");
        String url = null;
        for (String str : urls) {
            String[] s = str.split(",");
            if (s[0].equalsIgnoreCase(businessId)) {
                url = s[1];
            }
        }
        return url;
    }

    //获取转发参数
    private String getRequestParams(Request req) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("version", req.getVersion());
        jsonObj.put("businessId", req.getBusinessId());
        jsonObj.put("serviceType", req.getServiceType());
        jsonObj.put("accessToken", req.getAccessToken());
        jsonObj.put("requestId", req.getRequestId());
        String app_account_id = "";
        if (!"".equals(req.getAccessToken())) {

            app_account_id = JwtTokenUtil.getAccountIdFromToken(req.getAccessToken());

        }
        req.getData().put("app_account_id", app_account_id);
        jsonObj.put("data", req.getData());
        return jsonObj.toString();
    }

    //验证权限
    private boolean checkScope(String accessToken, String serviceType) {
        Map<String, Object> analysisToken = JwtTokenUtil.analysisToken(accessToken, 1);
        String scp = (String) analysisToken.get("scp");
        String[] urls = openapi_scope.split(";");
        for (String str : urls) {
            String[] s = str.split(",");
            if (s[0].equals(scp)) {
                String[] split = s[1].split("\\|");
                for (String string : split) {
                    if (serviceType.equals(string)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getOpenapi_url() {
        return openapi_url;
    }

    public static void setOpenapi_url(String openapi_url) {
        Handler.openapi_url = openapi_url;
    }


    public static String getOpenapi_scope() {
        return openapi_scope;
    }

    public static void setOpenapi_scope(String openapi_scope) {
        Handler.openapi_scope = openapi_scope;
    }

    public String getReleaseCollection() {
        return releaseCollection;
    }

    public void setReleaseCollection(String releaseCollection) {
        this.releaseCollection = releaseCollection;
    }
}
