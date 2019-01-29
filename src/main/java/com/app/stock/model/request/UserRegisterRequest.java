package com.app.stock.model.request;

import javax.validation.constraints.NotNull;

/**
 * Created by lmx
 * Date 2019/1/9
 */
public class UserRegisterRequest extends VerifyRequest {

    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "验证码不能为空")
    private String code;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "昵称不能为空")
    private String nickname;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
