package com.app.stock.serviceImpl;

import com.app.stock.mapper.UserSubjectDetailSelfMapper;
import com.app.stock.model.CostDetail;
import com.app.stock.model.UserSubjectDetail;
import com.app.stock.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lmx
 * Date 2019/1/18
 */
@Service
public class UserSubjectDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserSubjectDetailSelfMapper userSubjectDetailSelfMapper;

    @Override
    public int createUserDetail(CostDetail costDetail) {
        UserSubjectDetail userSubjectDetail = new UserSubjectDetail();
        userSubjectDetail.setCostDetailId(costDetail.getId());
        userSubjectDetail.setCreateTime(new Date());
        userSubjectDetail.setSubjectId(costDetail.getSubjectId());
        userSubjectDetail.setUserId(costDetail.getUserId());
        userSubjectDetailSelfMapper.insertSelective(userSubjectDetail);
        return 0;
    }
}
