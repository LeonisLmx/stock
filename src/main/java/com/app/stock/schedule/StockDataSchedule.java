package com.app.stock.schedule;

import com.app.stock.common.RedisUtil;
import com.app.stock.mapper.StockCommentSelfMapper;
import com.app.stock.service.ScheduleCrossService;
import com.app.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by lmx
 * Date 2018/12/29
 * 股票数据定时计算
 */
@Component
public class StockDataSchedule {

    private static Logger logger = LoggerFactory.getLogger(StockDataSchedule.class);

    @Autowired
    private ScheduleCrossService scheduleCrossService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void updateRedisDate(){
        logger.info("开始更新redis的交易日期");
        scheduleCrossService.achieveTradingDate();
        logger.info("更新redis的交易日期结束");
    }

    // 数据拉取 每天凌晨1点开始执行
    @Scheduled(cron = "0 0 16 * * ?")
    public void pullingData() throws Exception {
        logger.info("开始拉取数据");
        scheduleCrossService.pullingData();
    }


    //@Scheduled(cron = "0 0 16 * * ?")
    public void calcCross(){

    }
}
