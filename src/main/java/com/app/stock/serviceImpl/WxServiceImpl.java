package com.app.stock.serviceImpl;

import com.app.stock.service.CostDetailsService;
import com.app.stock.service.WxService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lmx
 * Date 2019/1/17
 */
public class WxServiceImpl extends CostDetailsService implements WxService {

    @Override
    public <T> int createCostDetail(T t, String orderNumber, HttpServletRequest request) {
        return 0;
    }

    @Override
    public void getWxOrder() {

    }
}
