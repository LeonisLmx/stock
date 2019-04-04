package com.app.stock.service;

/**
 * @Author mingxin Liu
 * @Date 2019-03-30 22:23
 * @Description //阿里云SMS service
 */
public interface SMSSerivce {

    public int sendSMS(String templateCode,String phone) throws Exception;
}
