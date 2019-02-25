package com.app.stock.model;

import com.app.stock.model.request.VerifyRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class WishTree{
    private Long id;

    private Long userId;

    private String wishMonth;

    private Integer isDelete;

    private Date createTime;

    private Date modifyTime;
    @NotNull(message = "愿望不能为空")
    private String wish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWishMonth() {
        return wishMonth;
    }

    public void setWishMonth(String wishMonth) {
        this.wishMonth = wishMonth;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }
}