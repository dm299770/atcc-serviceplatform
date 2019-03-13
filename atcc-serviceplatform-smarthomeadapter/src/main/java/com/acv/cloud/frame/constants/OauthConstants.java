package com.acv.cloud.frame.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
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


    public String getSECRET_BAIDU() {
        return SECRET_BAIDU;
    }

    @Autowired(required = false)
    public void setSECRET_BAIDU(@Value("${jwt.secret_baidu}") String SECRET_BAIDU) {
        this.SECRET_BAIDU = SECRET_BAIDU;
    }

    public String getSECRET_HU() {
        return SECRET_HU;
    }

    @Autowired(required = false)
    public void setSECRET_HU(@Value("${jwt.secret_hu}") String SECRET_HU) {
        this.SECRET_HU = SECRET_HU;
    }

    public String getSECRET_AUTO() {
        return SECRET_AUTO;
    }

    @Autowired(required = false)
    public void setSECRET_AUTO(@Value("${jwt.secret_auto}") String SECRET_AUTO) {
        this.SECRET_AUTO = SECRET_AUTO;
    }

    public String getSECRET_JD() {
        return SECRET_JD;
    }

    @Autowired(required = false)
    public void setSECRET_JD(@Value("${jwt.secret_jd}") String SECRET_JD) {
        this.SECRET_JD = SECRET_JD;
    }

    public int getCODE_EXPIREIN() {
        return CODE_EXPIREIN;
    }

    @Autowired(required = false)
    public void setCODE_EXPIREIN(@Value("${jwt.code_expirein}") int CODE_EXPIREIN) {
        this.CODE_EXPIREIN = CODE_EXPIREIN;
    }

    public Long getREFRESH_TOKEN_EXPIREIN() {
        return REFRESH_TOKEN_EXPIREIN;
    }

    @Autowired(required = false)
    public void setREFRESH_TOKEN_EXPIREIN(@Value("${jwt.refresh_token_expirein}") Long REFRESH_TOKEN_EXPIREIN) {
        this.REFRESH_TOKEN_EXPIREIN = REFRESH_TOKEN_EXPIREIN;
    }

    public Long getACCESS_TOKEN_EXPIREIN() {
        return ACCESS_TOKEN_EXPIREIN;
    }

    @Autowired(required = false)
    public void setACCESS_TOKEN_EXPIREIN(@Value("${jwt.access_token_expirein}") Long ACCESS_TOKEN_EXPIREIN) {
        this.ACCESS_TOKEN_EXPIREIN = ACCESS_TOKEN_EXPIREIN;
    }

    public String getAUTHORITY_LIST() {
        return AUTHORITY_LIST;
    }

    @Autowired(required = false)
    public void setAUTHORITY_LIST(@Value("${jwt.authority_list}") String AUTHORITY_LIST) {
        this.AUTHORITY_LIST = AUTHORITY_LIST;
    }
}
