package com.acv.cloud.frame.util;

import com.acv.cloud.frame.constants.RedisConstants;
import com.alibaba.fastjson.JSON;
import com.acv.cloud.frame.constants.OauthConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;
    private static final Logger logger = LoggerFactory.getLogger("JwtTokenUtil");

    //默认秘钥
    static String secret = "AVC-CHINA";

    /**
     * 验证accessToken是否过期 ,true为过期
     */
    public static boolean checkAccessToken(String accessToken) {
        Map<String, Object> map = analysisToken(accessToken, 0);
        //获取accessToken对应的解析密码
        String kid = (String) map.get("kid");
        if (kid != null) {
            if (kid.equals("CHRTSP01")) {
                secret = OauthConstants.SECRET_BAIDU;
            } else if (kid.equals("CHRTSP02")) {
                secret = OauthConstants.SECRET_HU;
            } else if (kid.equals("CHRTSP03")) {
                secret = OauthConstants.SECRET_AUTO;
            } else if (kid.equals("CHRTSP04")) {
                secret = OauthConstants.SECRET_JD;
            }
        }
        try {
            Jwts.parser().setSigningKey(new String(Base64.encodeBase64(secret.getBytes("UTF-8")), "UTF-8"))
                    .parseClaimsJws(accessToken)
                    .getBody();
            return false;
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                logger.error("accessToken已经过期：{}", accessToken);
            }
            return true;
        }
    }

    /**
     * 生成accessToken platform百度平台、hu德赛车机、auto高德
     *
     * @param map型数据
     * @return accessToken
     */
    public static String generateToken(Map<String, Object> params) {
        long expireIn = (Long) params.get("EXPIREIN");
        String scope = (String) params.get("SCOPE");
        String app_account_id = (String) params.get("APP_ACCOUNT_ID");
        //拼装header数据
        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        //拼装Payload数据
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("ver", "1");
        claims.put("typ", "AT");
        claims.put("iss", "CHRCNTSP01TUI3");
        if ("scp01001".equals(scope)) {
            header.put("kid", "CHRTSP01");
            claims.put("aud", "Baidu");
            claims.put("scp", "scp01001");
            secret = OauthConstants.SECRET_BAIDU;
        } else if ("scp01002".equals(scope)) {
            header.put("kid", "CHRTSP02");
            claims.put("aud", "Baidu");
            claims.put("scp", "scp01002");
            secret = OauthConstants.SECRET_HU;
        } else if ("scp01003".equals(scope)) {
            header.put("kid", "CHRTSP01");
            claims.put("aud", "Baidu");
            claims.put("scp", "scp01003");
            secret = OauthConstants.SECRET_BAIDU;
        } else if ("scp02001".equals(scope)) {
            header.put("kid", "CHRTSP03");
            claims.put("aud", "Auto");
            claims.put("scp", "scp02001");
            secret = OauthConstants.SECRET_AUTO;
        } else if ("read".equals(scope)) {
            header.put("kid", "CHRTSP04");
            claims.put("aud", "Jd");
            claims.put("scp", "scp03001");
            secret = OauthConstants.SECRET_JD;
        }
        claims.put("iat", String.valueOf(expireIn - OauthConstants.ACCESS_TOKEN_EXPIREIN));
        claims.put("exp", String.valueOf(expireIn));
        claims.put("styp", "T1");
        if (app_account_id == null) {
            app_account_id = "";
        }
        claims.put("id", app_account_id);
        claims.put("tnt", "Chery_CN");
        claims.put(RedisConstants.LOGIN_DEVICETYPE,params.get(RedisConstants.LOGIN_DEVICETYPE));
        claims.put(RedisConstants.LOGIN_DEVICENO,params.get(RedisConstants.LOGIN_DEVICENO));

        try {
            return Jwts.builder()
                    .setHeader(header)
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, new String(Base64.encodeBase64(secret.getBytes("UTF-8")), "UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持字符编码");
        }
        return null;
    }

    /**
     * 解析accessToken获取明文信息
     *
     * @param String accessToken  int flag(0:header 1:playload)
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> analysisToken(String accessToken, int flag) {
        Map<String, Object> map = null;
        String[] token_arr = accessToken.split("\\.");
        if (token_arr.length != 0) {
            try {
                String token_str = new String(Base64.decodeBase64(token_arr[flag]), "UTF-8");
                //Gson gson = new Gson();
                map = JSON.parseObject(token_str, (new HashMap<String, Object>()).getClass());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 生成refreshToken
     */
    public static String generateRefreshToken() {
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        String refreshToken = null;
        try {
            refreshToken = oauthIssuerImpl.refreshToken();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return refreshToken;
    }

    /**
     * 解析accessToken获取用户id(app_account_id)
     *
     * @param accessToken
     * @return app_account_id
     */
    public static String getAccountIdFromToken(String accessToken) {
        Map<String, Object> map = analysisToken(accessToken, 1);
        String app_account_id = String.valueOf(map.get("id"));
        return app_account_id;
    }
}

