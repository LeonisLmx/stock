package com.app.stock.service;

import com.app.stock.model.Stock;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/7
 */
public interface StockService {

    public List<Stock> selectAllByKeywords(String keywords);

    public List<Map<String,Object>> selectInfoByCode(String stockCode,String market);

    public List<Map<String,Object>> selectStockByCondition(Map<String,Object> map);
}
