package com.acv.cloud.frame.util;


import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.OauthConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseUtil {

    protected static final Logger logger = LoggerFactory.getLogger("oauth2");

    public static JSONObject getJson(String client_id, String operate_type, String error_code) {
        JSONObject json = new JSONObject();
        String error_description = "";
        json.put("error_code", Integer.parseInt(error_code));
        switch (error_code) {
            case OauthConstants.REQUEST_OVERTIME:
                error_description = OauthConstants.REQUEST_OVERTIME_DESCRIBE;
                break;
            case OauthConstants.INVALID_PARAMETER:
                error_description = OauthConstants.INVALID_PARAMETER_DESCRIBE;
                break;
            case OauthConstants.SERVER_EXCEPTION:
                error_description = OauthConstants.SERVER_EXCEPTION_DESCRIBE;
                break;
            case OauthConstants.INVALID_RESPONSE_TYPE:
                error_description = OauthConstants.INVALID_RESPONSE_TYPE_DESCRIBE;
                break;
            case OauthConstants.INVALID_SCOPE:
                error_description = OauthConstants.INVALID_SCOPE_DESCRIBE;
                break;
            case OauthConstants.INVALID_CLIENT_ID:
                error_description = OauthConstants.INVALID_CLIENT_ID_DESCRIBE;
                break;
            case OauthConstants.INVALID_CLIENT_ID_SECRET:
                error_description = OauthConstants.INVALID_CLIENT_ID_SECRET_DESCRIBE;
                break;
            case OauthConstants.INVALID_AUTH_CODE:
                error_description = OauthConstants.INVALID_AUTH_CODE_DESCRIBE;
                break;
            case OauthConstants.INVALID_REFRESH_TOKEN:
                error_description = OauthConstants.INVALID_REFRESH_TOKEN_DESCRIBE;
                break;
            case OauthConstants.INVALID_ACCESS_TOKEN:
                error_description = OauthConstants.INVALID_ACCESS_TOKEN_DESCRIBE;
                break;
        }
        json.put("error_description", error_description);
        logger.info("{}<{}>{}{}", client_id, operate_type, "错误请求：", json);
        return json;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static HttpEntity getAbnormalResponse(String client_id, String operate_type, String error_code) {
        return new ResponseEntity(getJson(client_id, operate_type, error_code), HttpStatus.OK);
    }
}
