package com.acv.cloud.models.fegin;



import java.io.Serializable;

/**
 * 推送实体类
 * Created by liyang on 2018/12/19.
 */
public class Notification implements Serializable {

    //    @Id
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Notification(String ids, String token,
                        String title, String context,
                        String phoneNum, String type,
                        String vin, Integer readflag,
                        String createDate, String userId, String imageURL) {
        this.ids = ids;
        this.token = token;
        this.title = title;
        this.context = context;
        this.phoneNum = phoneNum;
        this.type = type;
        this.vin = vin;
        this.readflag = readflag;
        this.createDate = createDate;
        this.userId = userId;
        this.imageURL = imageURL;
    }
}
