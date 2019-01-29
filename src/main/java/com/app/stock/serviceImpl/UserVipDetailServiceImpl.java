package com.app.stock.serviceImpl;

import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.mapper.UserVipDetailSelfMapper;
import com.app.stock.model.CostDetail;
import com.app.stock.model.User;
import com.app.stock.model.UserVipDetail;
import com.app.stock.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/18
 */
@Service
public class UserVipDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserVipDetailSelfMapper userVipDetailMapper;

    @Autowired
    private UserSelfMapper userSelfMapper;

    @Override
    public int createUserDetail(CostDetail costDetail) {
        Calendar calendar = Calendar.getInstance();
        List<UserVipDetail> userVipDetails = userVipDetailMapper.selectAllVipDetailsByUserId(costDetail.getUserId());
        Date now = new Date();
        if(userVipDetails != null && userVipDetails.size() > 0){
            now = userVipDetails.get(0).getFailureTime();
        }
        UserVipDetail userVipDetail = new UserVipDetail();
        userVipDetail.setCostDetailId(costDetail.getId());
        userVipDetail.setCreateTime(new Date());
        userVipDetail.setMonth(Integer.valueOf(costDetail.getMonth() + ""));
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,userVipDetail.getMonth());
        userVipDetail.setFailureTime(calendar.getTime());
        userVipDetail.setUserId(costDetail.getUserId());
        userVipDetailMapper.insertSelective(userVipDetail);
        return 0;
    }
}
