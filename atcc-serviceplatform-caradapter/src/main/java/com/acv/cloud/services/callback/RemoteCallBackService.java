package com.acv.cloud.services.callback;



import com.acv.cloud.jsonBean.remote.RemoteCallBackParameters;
import com.alibaba.fastjson.JSONObject;


/**
 * @description:登录相关的方法
 * @author:@leo.
 */
public interface RemoteCallBackService {
    /**
     * 车控回调接口
     *
     * @param vin车架号
     * @return
     */
    JSONObject remoteCallBack(RemoteCallBackParameters request);


}

