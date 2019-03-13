package com.acv.cloud.repository.mongotemplate;

/**
 * Created by liyang on 2018/12/24.
 */
public interface ISMSDao<T> {

    void insertSms(String phoneNum, String content, String createDate);
}
