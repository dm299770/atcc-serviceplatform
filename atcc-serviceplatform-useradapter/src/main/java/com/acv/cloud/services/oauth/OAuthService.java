package com.acv.cloud.services.oauth;


import com.acv.cloud.models.sys.TsUser;

import java.util.Map;

public interface OAuthService {

    //添加 refresh token
    public void addRefreshToken(Map<String, Object> params) throws Exception;

    //验证refresh token是否有效
    public boolean checkRefreshToken(String refreshToken) throws Exception;

    //更新refresh token
    public void updateRefreshToken(Map<String, Object> params) throws Exception;

    //查询app_account_id
    public String findAppAccountIDByRefreshToken(String refreshToken) throws Exception;

    //查询用户信息
    public TsUser findByPhoneNum(String phoneNum) throws Exception;

    //检验第三方用户合法性
    public Map<String, Object> checkOAuthThridparty(String client_id, String client_secret) throws Exception;

    //根据client_id添加第三方用户信息scope:权限范围 redirect_uri:重定向地址   此方法没用到
    public void updOAuthThridpartyMes(Map<String, Object> params) throws Exception;
}
