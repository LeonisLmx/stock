package com.app.stock.model.request;

import javax.validation.constraints.NotNull;

/**
 * Created by lmx
 * Date 2019/1/17
 */
public class AlipayRequest extends VerifyRequest{

    @NotNull(message = "订单类型不能为空")
    private Integer type;   // 付款的应用
    @NotNull(message = "条件不能为空")
    private Long conditionId;   // 课程ID或者vip月份时长
    //private String body;  //  付款的主体信息
    @NotNull(message = "订单标题不能为空")
    private String subject;  // 付款的标题名字
    private String timeoutExpress;  // 订单过期时间
    @NotNull(message = "订单价格不能为空")
    private String totalAmount;  // 付款价格
    @NotNull(message = "回调页面不能为空")
    private String successHtml;  // 付款完毕回调页

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeoutExpress() {
        return timeoutExpress == null?"30m":timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSuccessHtml() {
        return successHtml;
    }

    public void setSuccessHtml(String successHtml) {
        this.successHtml = successHtml;
    }
}
