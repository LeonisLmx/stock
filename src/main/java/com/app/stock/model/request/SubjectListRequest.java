package com.app.stock.model.request;

import javax.validation.constraints.NotNull;

/**
 * Created by lmx
 * Date 2019/1/15
 */
public class SubjectListRequest extends VerifyRequest{
    private String orderType;    // 排序类型
    private String order;     // 排序规则
    private Integer page;
    private Integer page_size;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getPage() {
        return page == null?1:page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage_size() {
        return page_size==null?10:page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
