package com.app.stock.model.request;

import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/14
 */
public class AccountSummarizeRequest extends VerifyRequest{

    private Long storeTypeParentId;
    private List<Long> storeTypeId;
    private String month;
    private String year;

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
