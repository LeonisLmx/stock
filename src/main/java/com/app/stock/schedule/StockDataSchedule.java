package com.app.stock.schedule;

import com.app.stock.common.RedisUtil;
import com.app.stock.config.redis.RedisExecutor;
import com.app.stock.mapper.StockCommentSelfMapper;
import com.app.stock.service.ScheduleCrossService;
import com.app.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

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

    @Autowired
    private RedisExecutor redisExecutor;

    @Scheduled(cron = "0 0 10 * * ?")
    public void updateRedisDate(){
        logger.info("开始更新redis的交易日期");
        scheduleCrossService.achieveTradingDate();
        logger.info("更新redis的交易日期结束");
    }

    // 数据拉取 每天下午16点开始执行
    @Scheduled(cron = "0 0 16 * * ?")
    public void pullingData() throws Exception {
        logger.info("开始拉取数据");
        scheduleCrossService.pullingData();
    }


    @Scheduled(cron = "0 0 18 * * ?")
    public void calcCross(){
        logger.info("开始计算十字星");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.calcCorss();
        logger.info("十字星计算结束");
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void calcYangLine(){
        logger.info("开始计算阳线");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.calcYangLine();
        logger.info("阳线计算结束");
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void longUnderLine(){
        logger.info("开始计算长下影线");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.longUnderLine();
        logger.info("长下影线计算结束");
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void hammerLine(){
        logger.info("开始计算锤子线");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.hammerLine();
        logger.info("锤子线计算结束");
    }

    @Scheduled(cron = "0 20 18 * * ?")
    public void KDJ(){
        logger.info("开始计算KDJ");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.KDJ();
        logger.info("KDJ计算结束");
    }

    @Scheduled(cron = "0 25 18 * * ?")
    public void MACD(){
        logger.info("开始计算MACD");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.MACD();
        logger.info("MACD计算结束");
    }

    @Scheduled(cron = "0 30 18 * * ?")
    public void BOLL(){
        logger.info("开始计算BOLL");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.BOLL();
        logger.info("BOLL计算结束");
    }

    @Scheduled(cron = "0 35 18 * * ?")
    public void WR(){
        logger.info("开始计算WR");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.WR();
        logger.info("WR计算结束");
    }

    @Scheduled(cron = "0 40 18 * * ?")
    public void V(){
        logger.info("开始计算V");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.V();
        logger.info("V计算结束");
    }

    @Scheduled(cron = "0 45 18 * * ?")
    public void SEA(){
        logger.info("开始计算海底捞月");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.SEA();
        logger.info("海底捞月计算结束");
    }

    @Scheduled(cron = "0 50 18 * * ?")
    public void MORE(){
        logger.info("开始计算均线多头");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.MORE();
        logger.info("均线多头计算结束");
    }

    @Scheduled(cron = "0 55 18 * * ?")
    public void THREEARMY(){
        logger.info("开始计算三红兵");
        if(!scheduleCrossService.calcToday()){
            logger.info("当前不是交易日期");
            return;
        }
        scheduleCrossService.THREEARMY();
        logger.info("三红兵计算结束");
    }
}
