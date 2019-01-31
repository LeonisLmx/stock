package com.app.stock.serviceImpl;

import com.app.stock.mapper.StockDataSelfMapper;
import com.app.stock.mapper.StockSelfMapper;
import com.app.stock.model.Stock;
import com.app.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Stock> selectAllByKeywords(String keywords) {
        return stockSelfMapper.selectAllByKeywords(keywords);
    }

    @Override
    public List<Map<String, Object>> selectInfoByCode(String stockCode,String market) {
        return stockDataSelfMapper.selectStockInfoByCode(stockCode,market);
    }
}
