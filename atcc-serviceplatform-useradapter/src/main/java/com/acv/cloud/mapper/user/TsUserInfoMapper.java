package com.acv.cloud.mapper.user;

import com.acv.cloud.models.sys.TmChargeAccount;
import com.acv.cloud.domain.model.user.TsUserInfo;

/**
 * 用户信息
 *
 * @author leo.
 */
public interface
TsUserInfoMapper {


    /**
     * 保存用户基本信息
     *
     * @param bean 用户bean
     */
    void save(TsUserInfo bean);

    /**
     * 保存用户账号信息
     *
     * @param bean 用户账户bean
     */
    void saveAccount(TmChargeAccount bean);

    /**
     * 更新用户基本信息(只更新一个属性)
     *
     * @param tsUserInfo 用户信息
     *
     */
    void updateByType(TsUserInfo tsUserInfo);

}
