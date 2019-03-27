package com.app.stock.service;

import com.app.stock.model.Stock;
import com.app.stock.model.request.StockDetailRequest;

import java.io.UnsupportedEncodingException;
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

    public List<Map<String,Object>> getFormualData(Map<String, Object> map);

    public List<Object> getStockDetails(StockDetailRequest stockDetailRequest) throws UnsupportedEncodingException;

    public List<Object> getSinaData(Integer page,Integer page_size,Integer type) throws Exception;
}
