package com.app.stock.schedule;

import com.app.stock.common.RedisUtil;
import com.app.stock.mapper.PetSelfMapper;
import com.app.stock.mapper.PetStockDetailSelfMapper;
import com.app.stock.mapper.StockDataSelfMapper;
import com.app.stock.model.Pet;
import com.app.stock.model.PetStockDetail;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lmx
 * Date 2019/2/20
 * 宠物相关定时操作
 */
@Component
public class PetDataSchedule {

    private static Logger logger = LoggerFactory.getLogger(PetDataSchedule.class);

    @Autowired
    private PetSelfMapper petSelfMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PetStockDetailSelfMapper petStockDetailSelfMapper;

    @Autowired
    private StockDataSelfMapper stockDataSelfMapper;

    // 每天0点更新pet表
    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePet(){
        logger.info("开始更新pet表的每日操作信息");
        petSelfMapper.updatePetScheduled();
    }

    // 每天下午3点01分计算宠物等级
    @Scheduled(cron = "0 1 15 * * ?")
    public void calcPetLevel(){
        logger.info("开始计算宠物等级");
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        String dateStr = redisUtil.getMap("common","trading_date");
        List<String> dateList = new Gson().fromJson(dateStr,List.class);
        String date = dateList.get(4);
        List<PetStockDetail> list = petStockDetailSelfMapper.selectAllCalcStocks();
        List<PetStockDetail> editList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        for(PetStockDetail entity:list){
            Pet pet = petSelfMapper.selectByPrimaryKey(entity.getPetId());
            BigDecimal todayPrice = stockDataSelfMapper.selectClosePrice(entity.getStockId(),date);
            // 如果当天刚好是股票的第五个交易日，那么需要修改卖出的时间和卖出价格
            if(sdf.parse(sdf.format(entity.getCreateTime()),new ParsePosition(0)).compareTo(sdf.parse(date,new ParsePosition(0))) == 0){
                entity.setsPrice(todayPrice);
                entity.setsTime(new Date());
                editList.add(entity);
            }
            BigDecimal percent = (todayPrice.subtract(entity.getbPrice())).multiply(new BigDecimal(100)).divide(entity.getbPrice(),2,BigDecimal.ROUND_HALF_UP);
            BigDecimal level = (new BigDecimal(100)).multiply(todayPrice.subtract(entity.getbPrice())).divide(entity.getbPrice(),4).divide(new BigDecimal(3),0,BigDecimal.ROUND_HALF_UP);
            pet.setLevel(pet.getLevel() + Integer.valueOf(level + ""));
            pet.setPercent(percent + "");
            petList.add(pet);
        }
        if(petList.size() > 0){
            petSelfMapper.batchUpdate(petList);
        }
        if(editList.size() > 0){
            petStockDetailSelfMapper.batchUpdate(editList);
        }
        logger.info("宠物等级计算结束");
    }
}
