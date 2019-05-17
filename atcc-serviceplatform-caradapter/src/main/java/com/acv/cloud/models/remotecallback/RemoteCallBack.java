package com.acv.cloud.models.remotecallback;

public class RemoteCallBack {

     private String vin;
     private String cmdid;
     private String requestid;
    private int result;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getCmdid() {
        return cmdid;
    }

    public void setCmdid(String cmdid) {
        this.cmdid = cmdid;
    }


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

