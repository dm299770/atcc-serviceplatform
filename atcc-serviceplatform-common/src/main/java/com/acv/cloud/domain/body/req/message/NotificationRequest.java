package com.acv.cloud.domain.body.req.message;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 推送历史消息分页实体类实体类
 * Created by liyang on 2019/01/12.
 */
@Document(collection = "Notification")
public class NotificationRequest implements Serializable {

    //@Id
    private String ids;//id属性是给mongodb用的，用@Id注解修饰
    private String token;//设备token
    private String title;//标题
    private String context;//消息推送内容
    private String phoneNum;//用户手机号(账号)
    private String type;//推送类型

    private String vin;//车架号
    private Integer readflag;//已读未读状态位
    private String comment;//备注
    private String createDate;//发送时间
    private String userId;//用户userId

    private Integer pageNum = 1; //当前页
    private Integer pageSize = 10;//每页显示数
    private Sort sort; // 排序条件
    private String imageURL;//图片路径

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getReadflag() {
        return readflag;
    }

    public void setReadflag(Integer readflag) {
        this.readflag = readflag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
