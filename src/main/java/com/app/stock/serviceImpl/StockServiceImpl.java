package com.app.stock.serviceImpl;

import com.app.stock.config.redis.RedisExecutor;
import com.app.stock.mapper.StockDataSelfMapper;
import com.app.stock.mapper.StockSelfMapper;
import com.app.stock.model.Stock;
import com.app.stock.service.StockService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/7
 */
@Service
public class StockServiceImpl implements StockService {

    private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockSelfMapper stockSelfMapper;

    @Autowired
    private StockDataSelfMapper stockDataSelfMapper;

    @Autowired
    private RedisExecutor redisExecutor;

    @Override
    public List<Stock> selectAllByKeywords(String keywords) {
        return stockSelfMapper.selectAllByKeywords(keywords);
    }

    @Override
    public List<Map<String, Object>> selectInfoByCode(String stockCode,String market) {
        return stockDataSelfMapper.selectStockInfoByCode(stockCode,market);
    }

    @Override
    public List<Map<String,Object>> selectStockByCondition(Map<String, Object> map) {
        List<String> condition = (List)map.get("condition");
        List<Map<String,Object>> list = new ArrayList<>();
        Gson gson = new Gson();
        for(String entity:condition){
            if(entity.equals("1")){
                List<Map<String,Object>> redisList = gson.fromJson(redisExecutor.getMap("CALC","CROSS"),List.class);
                list = addRepeatList(list,redisList);
            }else if(entity.equals("2")){
                List<Map<String,Object>> redisList = gson.fromJson(redisExecutor.getMap("CALC","YANGLINE"),List.class);
                list = addRepeatList(list,redisList);
            }else if(entity.equals("3")){
                List<Map<String,Object>> redisList = gson.fromJson(redisExecutor.getMap("CALC","LONGUNDERLINE"),List.class);
                list = addRepeatList(list,redisList);
            }else if(entity.equals("4")){
                List<Map<String,Object>> redisList = gson.fromJson(redisExecutor.getMap("CALC","HAMMERLINE"),List.class);
                list = addRepeatList(list,redisList);
            }
        }
        return list;
    }

    protected List<Map<String,Object>> addRepeatList(List<Map<String,Object>> originalList,List<Map<String,Object>> targetList){
        if(originalList == null || originalList.size() == 0){
            return targetList;
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Map<String,Object> entity:targetList){
            if(originalList.contains(entity)){
                resultList.add(entity);
            }
        }
        return resultList;
    }
}
