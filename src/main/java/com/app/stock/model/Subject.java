package com.app.stock.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class Subject {
    private Long id;

    @NotNull(message = "课程类别不能为空")
    private Long subjectTypeId;
    @NotNull(message = "老师不能为空")
    private Long teacherId;
    @NotNull(message = "课程名字不能为空")
    private String title;
    @NotNull(message = "课程简介不能为空")
    private String content;

    private String smallImg;
    @NotNull(message = "课程详情不能为空")
    private String detail;
    @NotNull(message = "课程普通价格不能为空")
    private BigDecimal commonPrice;
    @NotNull(message = "课程会员价格不能为空")
    private BigDecimal vipPrice;

    private Integer isDelete;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Long subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getCommonPrice() {
        return commonPrice;
    }

    public void setCommonPrice(BigDecimal commonPrice) {
        this.commonPrice = commonPrice;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
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
}