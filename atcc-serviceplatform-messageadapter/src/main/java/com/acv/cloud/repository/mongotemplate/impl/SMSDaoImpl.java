package com.acv.cloud.repository.mongotemplate.impl;

import com.acv.cloud.models.mongdb.sms.Attributes;
import com.acv.cloud.repository.mongotemplate.ISMSDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * 操作mongodb,把消息存入
 * Created by liyang on 2018/12/24.
 *
 * @param <T>
 */
@Repository
public class SMSDaoImpl<T> implements ISMSDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertSms(String phoneNum, String content, String createDate) {
        Attributes sms = new Attributes();
        sms.setContent(content);
        sms.setCreateDate(createDate);
        sms.setPhoneNum(phoneNum);
        mongoTemplate.insert(sms);
    }
}
