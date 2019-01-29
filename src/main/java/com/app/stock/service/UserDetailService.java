package com.app.stock.service;

import com.app.stock.model.CostDetail;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lmx
 * Date 2019/1/18
 */
public interface UserDetailService {

    // 新增详情表的情况
    @Transactional(propagation = Propagation.MANDATORY)
    public int createUserDetail(CostDetail costDetail);
}
