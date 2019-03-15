package com.acv.cloud.mapper.user;

import com.acv.cloud.models.sys.TmChargeAccount;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.models.sys.TsUserInfo;
import org.apache.ibatis.annotations.Param;

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
     * @param type  需要更新的属性名
     * @param value 需要更新的属性值
     */
    void updateByType(@Param("userId") String userId, @Param("type") String type, @Param("value") String value);

}
