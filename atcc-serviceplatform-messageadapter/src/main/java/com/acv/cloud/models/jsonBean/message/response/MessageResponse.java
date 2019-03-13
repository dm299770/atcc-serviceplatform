package com.acv.cloud.models.jsonBean.message.response;


/**
 * 推送通知历史消息返回实体
 * <p>
 * Created by liyang on 2019/01/10.
 */
public class MessageResponse {

    private String ids;
    private String title;
    private String context;
    private String createDate;
    private Integer readflag;
    private String imageURL;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getReadflag() {
        return readflag;
    }

    public void setReadflag(Integer readflag) {
        this.readflag = readflag;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public MessageResponse(String ids, String title, String context, String createDate, Integer readflag, String imageURL) {
        this.ids = ids;
        this.title = title;
        this.context = context;
        this.createDate = createDate;
        this.readflag = readflag;
        this.imageURL = imageURL;
    }
}
