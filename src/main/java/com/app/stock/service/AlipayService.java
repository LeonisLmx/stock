package com.app.stock.service;

import com.alipay.api.AlipayApiException;
import com.app.stock.model.request.AlipayRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lmx
 * Date 2019/1/17
 */
public interface AlipayService {

    @Transactional(propagation = Propagation.REQUIRED)
    public String getAlipayOrder(AlipayRequest alipayRequest, HttpServletRequest request, HttpServletResponse httpResponse);

    @Transactional(propagation = Propagation.REQUIRED)
    public String notifyAlipay(HttpServletRequest request) throws AlipayApiException;
}
