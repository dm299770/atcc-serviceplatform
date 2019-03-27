package com.acv.cloud.mapper.account;

import com.acv.cloud.models.jsonBean.account.responseJson.TtChargeFlow;
import com.acv.cloud.models.jsonBean.account.responseJson.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {

    UserAccount selectBalance(@Param("userAccount") String user_id);

    void upadteBalance(@Param("userAccount") String userAccount, @Param("balance") String balance, @Param("updateTime") String updateTime);


    void saveChargeFlow(@Param("id") String id, @Param("userAccount") String userAccount,
                        @Param("amount") Double moneyD, @Param("direction") Integer direction,
                        @Param("chargeDate") String updateTime, @Param("chargeFrom") String phoneNum,
                        @Param("chargeTo") String chargeTo, @Param("comment") String comment);

    List<TtChargeFlow> selectAll(@Param("userAccount") String user_id);

    TtChargeFlow selOrderNum(@Param("comment") String comment);

    UserAccount selBalance(@Param("userAccount") String user_id);
}