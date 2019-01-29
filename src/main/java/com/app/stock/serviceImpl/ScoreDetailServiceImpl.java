package com.app.stock.serviceImpl;

import com.app.stock.mapper.ScoreDetailSelfMapper;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.model.ScoreDetail;
import com.app.stock.model.User;
import com.app.stock.service.ScoreDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lmx
 * Date 2019/1/18
 */
@Service
public class ScoreDetailServiceImpl implements ScoreDetailService {

    @Autowired
    private ScoreDetailSelfMapper scoreDetailSelfMapper;

    @Autowired
    private UserSelfMapper userSelfMapper;

    @Override
    public int addScore(Long userId, int score, String fromType) {
        ScoreDetail scoreDetail = new ScoreDetail();
        scoreDetail.setUserId(userId);
        scoreDetail.setScore(score);
        scoreDetail.setFromType(fromType);
        scoreDetail.setCreateTime(new Date());
        scoreDetailSelfMapper.insertSelective(scoreDetail);
        User user = userSelfMapper.selectByPrimaryKey(userId);
        user.setScore(user.getScore() + score);
        userSelfMapper.updateByPrimaryKeySelective(user);
        return 0;
    }
}
