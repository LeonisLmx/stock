package com.app.stock.model.request;

import javax.validation.constraints.NotNull;

/**
 * Created by lmx
 * Date 2019/1/9
 */
public class UserLoginTokenRequest extends VerifyRequest {

    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "令牌不能为空")
    private String token;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
