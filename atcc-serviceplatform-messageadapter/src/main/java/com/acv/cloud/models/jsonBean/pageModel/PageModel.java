package com.acv.cloud.models.jsonBean.pageModel;

import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by liyang on 2019/01/14.
 *
 * @Description: MongoDB分页模型
 */
public class PageModel implements Serializable {
    /**
     * @Fields: serialVersionUID
     * @Todo: TODO
     */
    private static final long serialVersionUID = 1L;
    // 当前页
    private Integer pageNumber = 1;
    // 当前页面条数
    private Integer pageSize = 10;
    // 排序条件
    private Sort sort;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
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
}