package com.app.stock.model.request;

/**
 * Created by lmx
 * Date 2019/1/9
 */
public class AccountTypeRequest extends VerifyRequest {

    private Integer income;
    private Long id;

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
