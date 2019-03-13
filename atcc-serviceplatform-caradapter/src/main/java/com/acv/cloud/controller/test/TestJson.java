package com.acv.cloud.controller.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.models.vehiclestate.ALLVehicleState;
import com.acv.cloud.models.vehiclestate.Tirepressure;
import com.acv.cloud.models.vehiclestate.VehicleCategory;
import com.acv.cloud.models.vehiclestate.WindowStatus;


public class TestJson {
    public static void main(String args[]){
        ALLVehicleState allVehicleState = new ALLVehicleState();
        WindowStatus  windowStatus=new WindowStatus();
        Tirepressure tirepressure=new Tirepressure();
        VehicleCategory vehicleCategory=new VehicleCategory();
        allVehicleState.setVin("NISSAN0000000000");
        allVehicleState.setDiagnosedate("5555");
        allVehicleState.setFcphk(15F);
        allVehicleState.setTrunkstatus(0);
        allVehicleState.setSunroofstatus(0);
        allVehicleState.setRequestid("4564564");
        allVehicleState.setResoilcap(62F);
        allVehicleState.setDrivingstatus("Parking");
        allVehicleState.setChargingstatus("Normal");
        allVehicleState.setDoorstatus(0);
        allVehicleState.setUsablekm(564F);
        allVehicleState.setConsumerate(5258F);
        allVehicleState.setMaxkm(8536F);
        allVehicleState.setResoilcap(54f);
        allVehicleState.setRoadhaul(8567F);
        allVehicleState.setFcphk(96F);
        allVehicleState.setDiagnosedate("SADASDASDA");
        windowStatus.setBL(0);
        windowStatus.setBR(0);
        windowStatus.setFL(0);
        windowStatus.setFR(0);
        tirepressure.setBL(12F);
        tirepressure.setBR(32F);
        tirepressure.setFL(43F);
        tirepressure.setFR(0F);
        tirepressure.setBlstate(0);
        tirepressure.setBrstate(0);
        tirepressure.setFlstate(0);
        tirepressure.setFrstate(0);
        allVehicleState.setTirepressure(tirepressure);
        allVehicleState.setWindowstatus(windowStatus);
        allVehicleState.setResbatterycap(55F);
        vehicleCategory.setType("GTR");
        allVehicleState.setVehicleCategory(vehicleCategory);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(allVehicleState);
        System.out.println(jsonObject.toJSONString());

    }

}
