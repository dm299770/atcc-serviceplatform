package com.acv.cloud.jsonBean.remote.parameter;


import com.acv.cloud.domain.dto.car.AirConditionCtrl;

/**
 * 请求bean
 * @author GUO.ZJ
 */
public class AirConditionRequestParameter {
    private AirConditionCtrl data;

    public AirConditionCtrl getData() {
        return data;
    }

    public void setData(AirConditionCtrl data) {
        this.data = data;
    }
}
