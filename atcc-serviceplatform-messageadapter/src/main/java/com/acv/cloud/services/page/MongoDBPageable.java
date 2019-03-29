package com.acv.cloud.services.page;

import com.acv.cloud.models.jsonBean.message.request.NotificationRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by liyang on 2019/01/14.
 *
 * @Description: MongoDB分页公共类
 */
public class MongoDBPageable implements Serializable, Pageable {
    /**
     * @Fields: serialVersionUID
     * @Todo: TODO
     */
    private static final long serialVersionUID = 1L;

    NotificationRequest page;

    public NotificationRequest getPage() {
        return page;
    }

    public void setPage(NotificationRequest page) {
        this.page = page;
    }

    @Override
    public Pageable first() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getOffset() {
        // TODO Auto-generated method stub
        return (page.getPageNum() - 1) * page.getPageSize();
    }

    @Override
    public int getPageNumber() {
        // TODO Auto-generated method stub
        return page.getPageNum();
    }

    @Override
    public int getPageSize() {
        // TODO Auto-generated method stub
        return page.getPageSize();
    }


    @Override
    public boolean hasPrevious() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Pageable next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sort getSort() {
        // TODO Auto-generated method stub
        return page.getSort();
    }
}
