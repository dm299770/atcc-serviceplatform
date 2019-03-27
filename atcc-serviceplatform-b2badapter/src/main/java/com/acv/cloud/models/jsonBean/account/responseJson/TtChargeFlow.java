package com.acv.cloud.models.jsonBean.account.responseJson;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 金额流水记录
 */
public class TtChargeFlow implements java.io.Serializable {

    private String id;          //流水单号
    private String userAccount; //关联用户id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String chargeDate;    //下单日期
    private String chargeFrom;  //发起方
    private String chargeTo;    //到达方
    private Integer direction;  //增减标识
    private Double amount;      //金额
    private String comment;     //备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getChargeFrom() {
        return chargeFrom;
    }

    public void setChargeFrom(String chargeFrom) {
        this.chargeFrom = chargeFrom;
    }

    public String getChargeTo() {
        return chargeTo;
    }

    public void setChargeTo(String chargeTo) {
        this.chargeTo = chargeTo;
    }

    public Integer getDirection() {
        return direction;
    }

    public Integer setDirection(Integer direction) {
        this.direction = direction;
        return direction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
