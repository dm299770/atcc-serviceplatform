package com.acv.cloud.models.jsonBean.account.responseJson;

/**
 * 用户余额
 */
public class UserAccount implements java.io.Serializable{

    private String userAccount; //用户关联ID
    private String balance;     //余额
    private String createTime;    //创建时间
    private String updateTime;    //修改时间

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
