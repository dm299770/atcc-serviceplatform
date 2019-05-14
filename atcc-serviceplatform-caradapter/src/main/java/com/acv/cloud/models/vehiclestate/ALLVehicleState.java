package com.acv.cloud.models.vehiclestate;


/**
 *EV车辆状态
 * @author guo.zj
 */
public class ALLVehicleState implements java.io.Serializable {
    //请求ID
    private String requestid;
    //剩余电量
    private Float resbatterycap;
    //后备箱状态
    private int trunkstatus;
    //驾驶状态
    private String drivingstatus;
    //充电状态
    private String chargingstatus;
    //胎压
    private Tirepressure tirepressure;
    //车锁状态
    private String doorstatus;
    //天窗状态
    private int sunroofstatus;
    //上报时间
    private long updatetime;
    //车窗
    private WindowStatus windowstatus;
    //续航里程
    private Float usablekm;
    //百公里耗电
    private Float consumerate;
    //vin
    private String vin;
    //最大里程
    private Float maxkm;
    //最大里程
    private Float resoilcap;
    //剩余油量
    private Float roadhaul;
    //百公里油耗
    private Float fcphk;
    //最近诊断日期
    private String diagnosedate;
    //车辆种类
    private VehicleCategory vehicleCategory;
    //经度
    private double lat;
    //维度
    private double lon;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }



    public int getTrunkstatus() {
        return trunkstatus;
    }

    public void setTrunkstatus(int trunkstatus) {
        this.trunkstatus = trunkstatus;
    }

    public String getDrivingstatus() {
        return drivingstatus;
    }

    public void setDrivingstatus(String drivingstatus) {
        this.drivingstatus = drivingstatus;
    }

    public String getChargingstatus() {
        return chargingstatus;
    }

    public void setChargingstatus(String chargingstatus) {
        this.chargingstatus = chargingstatus;
    }


    public int getSunroofstatus() {
        return sunroofstatus;
    }

    public void setSunroofstatus(int sunroofstatus) {
        this.sunroofstatus = sunroofstatus;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }


    public Float getResbatterycap() {
        return resbatterycap;
    }

    public void setResbatterycap(Float resbatterycap) {
        this.resbatterycap = resbatterycap;
    }

    public WindowStatus getWindowstatus() {
        return windowstatus;
    }

    public void setWindowstatus(WindowStatus windowstatus) {
        this.windowstatus = windowstatus;
    }

    public Tirepressure getTirepressure() {
        return tirepressure;
    }

    public void setTirepressure(Tirepressure tirepressure) {
        this.tirepressure = tirepressure;
    }

    public Float getUsablekm() {
        return usablekm;
    }

    public void setUsablekm(Float usablekm) {
        this.usablekm = usablekm;
    }

    public Float getConsumerate() {
        return consumerate;
    }

    public void setConsumerate(Float consumerate) {
        this.consumerate = consumerate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Float getMaxkm() {
        return maxkm;
    }

    public void setMaxkm(Float maxkm) {
        this.maxkm = maxkm;
    }

    public Float getResoilcap() {
        return resoilcap;
    }

    public void setResoilcap(Float resoilcap) {
        this.resoilcap = resoilcap;
    }

    public Float getRoadhaul() {
        return roadhaul;
    }

    public void setRoadhaul(Float roadhaul) {
        this.roadhaul = roadhaul;
    }

    public Float getFcphk() {
        return fcphk;
    }

    public void setFcphk(Float fcphk) {
        this.fcphk = fcphk;
    }

    public String getDiagnosedate() {
        return diagnosedate;
    }

    public void setDiagnosedate(String diagnosedate) {
        this.diagnosedate = diagnosedate;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getDoorstatus() {
        return doorstatus;
    }

    public void setDoorstatus(String doorstatus) {
        this.doorstatus = doorstatus;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
