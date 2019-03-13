package com.acv.cloud.models.sys;

import java.util.Date;

/**
 * 用户账户信息信息
 *
 * @author leo.
 */
public class TmChargeAccount implements java.io.Serializable {

    private String userAccount;//关联TsUser主键
    private String balance;//余额
    Date createTime;//创建时间
    Date updateTime;//更新时间

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
