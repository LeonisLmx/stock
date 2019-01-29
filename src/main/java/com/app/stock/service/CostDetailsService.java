package com.app.stock.service;

import com.app.stock.mapper.CostDetailSelfMapper;
import com.app.stock.model.CostDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lmx
 * Date 2019/1/17
 */
@Service
public abstract class CostDetailsService {

    @Resource
    private CostDetailSelfMapper costDetailSelfMapper;

    // 创建账单
    public abstract <T> int createCostDetail(T t, String orderNumber, HttpServletRequest request);

    // 修改订单状态
    @Transactional(propagation = Propagation.MANDATORY)
    public CostDetail editCostStatus(String orderNumber, Integer status){
        CostDetail costDetail = costDetailSelfMapper.selectPrimaryKeyByOrderNumber(orderNumber);
        costDetail.setStatus(status);
        costDetailSelfMapper.updateByPrimaryKeySelective(costDetail);
        return costDetail;
    }
}
