package com.app.stock.config.desgin;

import com.app.stock.common.commonEnum.OrderTypeEnum;
import com.app.stock.model.CostDetail;
import com.app.stock.service.UserDetailService;
import com.app.stock.serviceImpl.UserSubjectDetailServiceImpl;
import com.app.stock.serviceImpl.UserVipDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lmx
 * Date 2019/1/18
 */
@Component
public class UserDetailFactory {

    @Autowired
    private UserVipDetailServiceImpl userVipDetailService;

    @Autowired
    private UserSubjectDetailServiceImpl userSubjectDetailService;

    public UserDetailService getFactory(CostDetail costDetail){
        if(costDetail.getType().equals(OrderTypeEnum.getName(1))){
            return userSubjectDetailService;
        }else if(costDetail.getType().equals(OrderTypeEnum.getName(2))){
            return userVipDetailService;
        }else{
            return null;
        }
    }
}
