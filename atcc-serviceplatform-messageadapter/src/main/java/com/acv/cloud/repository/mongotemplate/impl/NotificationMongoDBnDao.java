package com.acv.cloud.repository.mongotemplate.impl;

import com.acv.cloud.domain.body.req.message.NotificationRequest;
import com.acv.cloud.domain.body.req.notification.Notification;
import com.acv.cloud.domain.body.resp.message.MessageResponse;
import com.acv.cloud.models.sys.TsUser;
import com.acv.cloud.repository.mongotemplate.INotificationMongoDBDao;
import com.acv.cloud.services.page.MongoDBPageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * 操作mongodb
 * <p>
 * Created by liyang on 2018/12/20.
 */
@Repository
public class NotificationMongoDBnDao<T> implements INotificationMongoDBDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertList(String ids, String phoneNum, String token, String title,
                           String vin, String context, String createDate,
                           TsUser tsUser, String type, Integer readflag, String imageURL) {
        Notification no = new Notification(ids, token,
                title, context,
                phoneNum, type,
                vin, readflag, createDate,
                tsUser.getUserId(), imageURL);
        mongoTemplate.insert(no);
    }

    @Override
    public void insertAll(String ids, String phoneNum, String token, String title,
                          String vin, String context, String createDate,
                          String userId, String type, Integer readflag, String imageURL) {
        Notification no = new Notification(ids, token,
                title, context,
                phoneNum, type,
                vin, readflag, createDate,
                userId, imageURL);
        mongoTemplate.insert(no);
    }

    @Override
    public List<MessageResponse> queryList(String phoneNum, String type, Integer pageSize, Integer pageNum) {
        MongoDBPageable pageable = new MongoDBPageable();
        NotificationRequest pm = new NotificationRequest();
        //判断推送查询类型
        Query query = new Query();
        logger.info("消息类型:" + type);
        if (type == "all" || "all".equals(type)) {
            query.addCriteria(Criteria.where("phoneNum").is(phoneNum));
        } else {
            query.addCriteria(Criteria.where("type").is(type).and("phoneNum").is(phoneNum));
        }
        List<Order> orders = new ArrayList<Order>();  //排序
        orders.add(new Order(Direction.DESC, "createDate"));
        Sort sort = new Sort(orders);
        pm.setSort(sort);
        pm.setPageNum(pageNum);
        // 每页条数
        pm.setPageSize(pageSize);
        // 排序
        pm.setSort(sort);
        pageable.setPage(pm);
        List<NotificationRequest> noResponse = mongoTemplate.find(query.with(pageable), NotificationRequest.class);
        logger.info("历史消息查询结果:" + noResponse);
        int count = (int) mongoTemplate.count(query, NotificationRequest.class);
        logger.info("查询总条数为" + count);
        List<MessageResponse> messagesList = new ArrayList<>();
        for (NotificationRequest notificationRequestList : noResponse) {
            String title = notificationRequestList.getTitle();
            String context = notificationRequestList.getContext();
            String createDate = notificationRequestList.getCreateDate().substring(6, 16);
            Integer readflag = notificationRequestList.getReadflag();
            String ids = notificationRequestList.getIds();
            String imageURL = notificationRequestList.getImageURL();
            MessageResponse messageResponse = new MessageResponse(ids, title, context, createDate, readflag, imageURL);
            messagesList.add(messageResponse);
        }
        Page<MessageResponse> pageList = new PageImpl<MessageResponse>(messagesList, pageable, count);
        logger.info("历史消息分页结果:" + pageList);
        List<MessageResponse> messageResponse = pageList.getContent();
        logger.info("历史消息返回结果:" + messageResponse);
        return messageResponse;
    }

    @Override
    public void updateUnRead(String ids, Integer readflag) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ids").is(ids));
        Update update = Update.update("readflag", 1);
        mongoTemplate.updateMulti(query, update, Notification.class);

    }

    @Override
    public void delMessage(String ids) {
        Query query = new Query(Criteria.where("ids").is(ids));
        long count = mongoTemplate.remove(query, Notification.class).getDeletedCount();
    }
}
