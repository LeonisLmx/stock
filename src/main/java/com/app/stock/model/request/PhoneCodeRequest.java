package com.app.stock.model.request;

import org.jboss.logging.Message;

import javax.validation.constraints.NotNull;


/**
 * Created by lmx
 * Date 2019/1/9
 */
public class PhoneCodeRequest extends VerifyRequest {

    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "验证码类型不能为空")
    private Integer type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
