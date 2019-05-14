package com.acv.cloud.jsonBean.remote.remotemodel;

import com.acv.cloud.models.remote.AirCondition;

/**
 * 空调
 * @author guo.zj
 */
public class AirConditionCtrl {

    //vin
    private String vin;
    //操作
    private int action;
    //空调
    private AirCondition acparam;

    private String cmdid;

    private int smsflag;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public AirCondition getAcparam() {
        return acparam;
    }

    public void setAcparam(AirCondition acparam) {
        this.acparam = acparam;
    }

    public int getSmsflag() {
        return smsflag;
    }

    public void setSmsflag(int smsflag) {
        this.smsflag = smsflag;
    }

    public String getCmdid() {
        return cmdid;
    }

    public void setCmdid(String cmdid) {
        this.cmdid = cmdid;
    }
}
