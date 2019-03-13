package com.acv.cloud.repository.redistemplate;

/**
 * 操作redis通过phoneNum去查找设备token
 * Created by liyang on 2018/12/19.
 *
 */
public interface INotificationDao {

   String getDeviceToken(String phoneNums);
}
