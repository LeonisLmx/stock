package com.app.stock.model.request;

/**
 * Created by lmx
 * Date 2019/1/9
 */
public class VerifyRequest {

    private String sign;
    private String nonce_str;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }
}
