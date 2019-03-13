package com.acv.cloud.constants.ua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jwt")
public class OauthConstants {
    //错误码
    public static final String INVALID_RESPONSE_TYPE = "901";
    public static final String INVALID_SCOPE = "902";
    public static final String INVALID_CLIENT_ID = "904";
    public static final String INVALID_CLIENT_ID_SECRET = "905";
    public static final String INVALID_AUTH_CODE = "906";
    public static final String INVALID_REFRESH_TOKEN = "907";
    public static final String INVALID_ACCESS_TOKEN = "908";
    public static final String INVALID_PARAMETER = "400";
    public static final String SERVER_EXCEPTION = "500";
    public static final String REQUEST_OVERTIME = "201";
    //错误码描述
    public static final String INVALID_RESPONSE_TYPE_DESCRIBE = "授权服务器不支持客户端所指定的response_type";
    public static final String INVALID_SCOPE_DESCRIBE = "scope无效";
    public static final String INVALID_CLIENT_ID_DESCRIBE = "client_id不存在";
    public static final String INVALID_CLIENT_ID_SECRET_DESCRIBE = "client_id和client_secret不匹配";
    public static final String INVALID_AUTH_CODE_DESCRIBE = "授权码无效或已过期";
    public static final String INVALID_REFRESH_TOKEN_DESCRIBE = "refresh_token无效或已过期";
    public static final String INVALID_ACCESS_TOKEN_DESCRIBE = "accessToken无效或已过期";
    public static final String INVALID_PARAMETER_DESCRIBE = "请求参数不符合协议规范";
    public static final String SERVER_EXCEPTION_DESCRIBE = "服务器异常";
    public static final String REQUEST_OVERTIME_DESCRIBE = "请求超时";
    //路径分隔符
    public static String FILE_SEPARATOR = System.getProperty("file.separator");


    //CHRTSP01秘钥

    public static String SECRET_BAIDU;
    //CHRTSP02秘钥

    public static String SECRET_HU;
    //CHRTSP03秘钥

    public static String SECRET_AUTO;
    //CHRTSP04秘钥

    public static String SECRET_JD;
    //授权码过期时间

    public static int CODE_EXPIREIN;
    //refresh_token过期时间

    public static Long REFRESH_TOKEN_EXPIREIN;
    //access_token过期时间

    public static Long ACCESS_TOKEN_EXPIREIN;
    //权限_连接

    public static String AUTHORITY_LIST;

    public static String getSecretBaidu() {
        return SECRET_BAIDU;
    }

    public static void setSecretBaidu(String secretBaidu) {
        SECRET_BAIDU = secretBaidu;
    }

    public static String getSecretHu() {
        return SECRET_HU;
    }

    public static void setSecretHu(String secretHu) {
        SECRET_HU = secretHu;
    }

    public static String getSecretAuto() {
        return SECRET_AUTO;
    }

    public static void setSecretAuto(String secretAuto) {
        SECRET_AUTO = secretAuto;
    }

    public static String getSecretJd() {
        return SECRET_JD;
    }

    public static void setSecretJd(String secretJd) {
        SECRET_JD = secretJd;
    }

    public static int getCodeExpirein() {
        return CODE_EXPIREIN;
    }

    public static void setCodeExpirein(int codeExpirein) {
        CODE_EXPIREIN = codeExpirein;
    }

    public static Long getRefreshTokenExpirein() {
        return REFRESH_TOKEN_EXPIREIN;
    }

    public static void setRefreshTokenExpirein(Long refreshTokenExpirein) {
        REFRESH_TOKEN_EXPIREIN = refreshTokenExpirein;
    }

    public static Long getAccessTokenExpirein() {
        return ACCESS_TOKEN_EXPIREIN;
    }

    public static void setAccessTokenExpirein(Long accessTokenExpirein) {
        ACCESS_TOKEN_EXPIREIN = accessTokenExpirein;
    }

    public static String getAuthorityList() {
        return AUTHORITY_LIST;
    }

    public static void setAuthorityList(String authorityList) {
        AUTHORITY_LIST = authorityList;
    }
}
