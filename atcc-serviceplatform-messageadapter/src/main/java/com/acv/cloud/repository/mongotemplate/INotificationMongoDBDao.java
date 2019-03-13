package com.acv.cloud.repository.mongotemplate;

import com.acv.cloud.models.jsonBean.message.response.MessageResponse;
import com.acv.cloud.models.sys.TsUser;

import java.util.List;

/**
 * Created by liyang on 2018/12/20.
 */
public interface INotificationMongoDBDao<T> {

    void insertList(String ids, String phoneNum, String token,
                    String title, String vin, String context,
                    String createDate, TsUser userId,
                    String type, Integer readflag, String imageURL);

    void insertAll(String ids, String phoneNum, String token,
                   String title, String vin, String context,
                   String createDate, String userId,
                   String type, Integer readflag, String imageURL);

    List<MessageResponse> queryList(String phoneNum, String type, Integer pageSize, Integer pageNum);

    void updateUnRead(String ids, Integer readflag);

    void delMessage(String ids);

}
