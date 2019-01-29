package com.app.stock.model.request;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/14
 */
public class AccountListRequest extends VerifyRequest {

    private Long storeTypeParentId;
    private List<Long> storeTypeId;
    private String payType;
    private BigDecimal cost;
    private String content;
    private String startTime;
    private String endTime;
    private Integer page;
    private Integer page_size;

    public Long getStoreTypeParentId() {
        return storeTypeParentId;
    }

    public void setStoreTypeParentId(Long storeTypeParentId) {
        this.storeTypeParentId = storeTypeParentId;
    }

    public List<Long> getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(List<Long> storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
