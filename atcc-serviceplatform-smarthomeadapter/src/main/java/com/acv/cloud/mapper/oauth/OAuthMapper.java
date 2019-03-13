package com.acv.cloud.mapper.oauth;

import com.acv.cloud.models.sys.TsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface OAuthMapper {

    //添加 refresh token
    public void addRefreshToken(Map<String, Object> params) throws Exception;

    //验证refresh token是否有效
    public String checkRefreshToken(@Param("REFRESHTOKEN") String refreshToken) throws Exception;

    //更新refresh token
    public void updateRefreshToken(Map<String, Object> params) throws Exception;

    //查询app_account_id
    public String findAppAccountIDByRefreshToken(@Param("REFRESHTOKEN") String refreshToken) throws Exception;

    //查询用户信息
    public TsUser findUserByPhoneNum(@Param("PHONENUM") String phoneNum) throws Exception;

    //检验第三方用户合法性
    public Map<String, Object> checkOAuthThridparty(@Param("CLIENT_ID") String client_id, @Param("CLIENT_SECRET") String client_secret) throws Exception;

    //根据client_id添加第三方用户信息scope:权限范围 redirect_uri:重定向地址
    public void updOAuthThridpartyMes(Map<String, Object> params) throws Exception;
}
