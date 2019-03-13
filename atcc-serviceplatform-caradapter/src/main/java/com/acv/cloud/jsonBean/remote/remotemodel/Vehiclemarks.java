package com.acv.cloud.jsonBean.remote.remotemodel;

import java.util.Map;

/**
 * 车辆诊断
 * @author guo.zj
 */
public class Vehiclemarks {


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String score;

    public Map<String, String>[] getSystem() {
        return system;
    }

    public void setSystem(Map<String, String>[] system) {
        this.system = system;
    }

    private  Map<String, String>[] system;


    private String diagonsedate;

    public String getDiagonsedate() {
        return diagonsedate;
    }

    public void setDiagonsedate(String diagonsedate) {
        this.diagonsedate = diagonsedate;
    }
}
