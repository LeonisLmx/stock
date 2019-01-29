package com.app.stock.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lmx
 * Date 2019/1/18
 */
public interface ScoreDetailService {

    @Transactional(propagation = Propagation.SUPPORTS)
    public int addScore(Long userId,int score,String fromType);
}
