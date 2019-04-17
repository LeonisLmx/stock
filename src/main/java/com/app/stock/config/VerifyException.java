package com.app.stock.config;

/**
 * Created by lmx
 * Date 2019/1/7
 * 自定义异常，用于验证失败
 */
public class VerifyException extends Exception {

    public VerifyException(String message){
        super(message);
    }
}
