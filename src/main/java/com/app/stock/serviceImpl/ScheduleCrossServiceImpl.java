package com.app.stock.serviceImpl;

import com.app.stock.common.HttpClient;
import com.app.stock.common.RedisUtil;
import com.app.stock.mapper.StockDataSelfMapper;
import com.app.stock.mapper.StockSelfMapper;
import com.app.stock.model.Stock;
import com.app.stock.model.StockData;
import com.app.stock.service.ScheduleCrossService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Bidi;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by lmx
 * Date 2019/1/23
 */
@Service
public class ScheduleCrossServiceImpl implements ScheduleCrossService {

    private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private StockSelfMapper stockSelfMapper;

    @Autowired
    private StockDataSelfMapper stockDataSelfMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void achieveTradingDate() {
        String response = null;
            // 获得最近的60个交易日
            String url = "https://api.shenjian.io/?appid=25a308aa0f9fe382bbfad6b40e922cc8&code=000001&index=true&k_type=day&fq_type=qfq";
            try {
                response = HttpClient.Get(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String,Object> map = new Gson().fromJson(response,Map.class);
            List<Map<String,Object>> list = new Gson().fromJson(map.get("data").toString(),new TypeToken<List<Map<String,Object>>>(){}.getType());
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return o2.get("date").toString().compareTo(o1.get("date").toString());
                }
            });
            List<Map<String,Object>> dateList = list.subList(0,60);
            Collections.sort(dateList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return o1.get("date").toString().compareTo(o2.get("date").toString());
                }
            });
            List<String> redisDateList = new LinkedList<>();
            for(Map<String,Object> entity:dateList){
                redisDateList.add(entity.get("date").toString());
            }
            redisUtil.addMap("common","trading_date",redisDateList);
    }

    @Override
    public void pullingData(){
        List<String> dateList = new Gson().fromJson(redisUtil.getMap("common","trading_date"), List.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        // 说明今天不是交易日，就不用拉取数据
        if(!date.equals(dateList.get(dateList.size() - 1))){
            return;
        }
        List<Map<String,Object>> stocks = stockSelfMapper.selectAllStockCode();
        AtomicInteger automicInteger = new AtomicInteger(0);
        for(Map<String,Object> str:stocks){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            automicInteger.incrementAndGet();
            String url = "https://api.shenjian.io/?appid=25a308aa0f9fe382bbfad6b40e922cc8&code=" + str.get("stock_code") + "&index=false&k_type=day&fq_type=qfq&start_date=" + date;
            String response = null;
            try {
                response = HttpClient.Get(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String,Object> map = new Gson().fromJson(response,Map.class);
            System.out.println(response);
            if("0".equals(map.get("error_code").toString().split("[.]")[0])){
                try {
                    List<Map<String, Object>> list = new Gson().fromJson(map.get("data").toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                    List<StockData> insertList = new ArrayList<>();
                    for (Map<String, Object> entity : list) {
                        StockData stockData = new StockData();
                        stockData.setStockId(Long.valueOf(str.get("id").toString()));
                        stockData.setOpen(BigDecimal.valueOf(Double.valueOf(entity.get("open").toString())));
                        stockData.setClose(BigDecimal.valueOf(Double.valueOf(entity.get("close").toString())));
                        stockData.setLow(BigDecimal.valueOf(Double.valueOf(entity.get("low").toString())));
                        stockData.setHigh(BigDecimal.valueOf(Double.valueOf(entity.get("high").toString())));
                        stockData.setVolume(BigDecimal.valueOf(Double.valueOf(entity.get("volume").toString())));
                        stockData.setTradingDay(entity.get("date").toString());
                        insertList.add(stockData);
                    }
                    stockDataSelfMapper.batchInsert(insertList);
                }catch (Exception e){
                    e.printStackTrace();
                    Map<String,Object> entityMap = new Gson().fromJson(map.get("data").toString(), Map.class);
                    StockData stockData = new StockData();
                    stockData.setStockId(Long.valueOf(str.get("id").toString()));
                    stockData.setOpen(BigDecimal.valueOf(Double.valueOf(entityMap.get("open").toString())));
                    stockData.setClose(BigDecimal.valueOf(Double.valueOf(entityMap.get("close").toString())));
                    stockData.setLow(BigDecimal.valueOf(Double.valueOf(entityMap.get("low").toString())));
                    stockData.setHigh(BigDecimal.valueOf(Double.valueOf(entityMap.get("high").toString())));
                    stockData.setVolume(BigDecimal.valueOf(Double.valueOf(entityMap.get("volume").toString())));
                    stockData.setTradingDay(entityMap.get("date").toString());
                    stockDataSelfMapper.insertSelective(stockData);
                }
            }
            logger.info("拉取股票代码" + str + "数据成功");
            System.out.println("当前是第" + automicInteger + "条股票数据");
        }
    }

    protected Map<String,Object> getStockDatas(Integer num){
        String dateStr = redisUtil.getMap("common","trading_date");
        if(StringUtils.isBlank(dateStr)){
            return null;
        }
        List<String> date = new Gson().fromJson(dateStr,List.class);
        Collections.sort(date, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        if(date.size() < num){
            return null;
        }
        date = date.subList(0,num);    // 获取日期最小的num个交易日
        Map<String,Object> map = new HashMap<>();
        List<StockData> dataList = stockDataSelfMapper.selectDatasByTradingDay(date);
        for(StockData entity:dataList){
            List<StockData> stockData = new ArrayList<>();
            if(map.get(entity.getStockId().toString()) != null){
                stockData = (List)map.get(entity.getStockId().toString());
            }
            stockData.add(entity);
            map.put(entity.getStockId().toString(),stockData);
        }
        for(Map.Entry entity:map.entrySet()){
            List<StockData> stockDataList = (List)entity.getValue();
            // 如果在交易日中有股票是停盘的，则把数据补成4条
            if(stockDataList.size() < num){
                List<StockData> list = stockDataSelfMapper.selectStocksByStockId(Long.valueOf(entity.getKey() + ""));
                // 循环添加至参数的个数
                loop:
                for(StockData stockData:list){
                    // 不能重复添加
                    for(StockData current:stockDataList){
                        if(current.getTradingDay().equals(stockData.getTradingDay())){
                            continue loop;
                        }
                    }
                    stockDataList.add(stockData);
                    if(stockDataList.size() == num){
                        break;
                    }
                }
            }
            if(stockDataList.size() < num){
                continue;
            }
            Collections.sort(stockDataList, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getTradingDay().compareTo(o2.getTradingDay());
                }
            });
        }
        return map;
    }

    // 计算股票十字星
    @Override
    public void calcCorss() {
        Long start = System.currentTimeMillis();
        logger.info("开始计算十字星");
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(3);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            if(set.contains(current.getKey().toString())){
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            for (StockData entity : dataList) {
                if (entity.getOpen() != null && entity.getOpen().compareTo(new BigDecimal(0)) != 0) {
                    double firstValue = Math.abs((entity.getClose().subtract(entity.getOpen())).divide(entity.getOpen(), 3, ROUND_HALF_UP).doubleValue());
                    if (firstValue > 0.001 && firstValue < 0.005
                            && entity.getHigh().compareTo(new BigDecimal(Math.max(entity.getClose().doubleValue(), entity.getOpen().doubleValue()))) > 0
                            && entity.getLow().compareTo(new BigDecimal(Math.min(entity.getClose().doubleValue(), entity.getOpen().doubleValue()))) < 0) {
                        set.add(entity.getStockId().toString());
                    }
                }
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","CROSS",stockInfo);
        Long end = System.currentTimeMillis();
        logger.info("计算结束十字星，总耗时" + (end - start) / 1000);
    }

    // 股票阳线
    @Override
    public void calcYangLine() {
        logger.info("开始计算股票阳线");
        Long startTime = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(4);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry entity:map.entrySet()) {
            if(set.contains(entity.getKey().toString())){
                continue;
            }
            List<StockData> stockDataList = (List)entity.getValue();
            for (int i = 1; i < stockDataList.size(); i++) {
                if (stockDataList.get(i).getClose().compareTo(stockDataList.get(i).getHigh()) == 0 &&
                        (stockDataList.get(i).getClose().subtract(stockDataList.get(i).getOpen())).divide(stockDataList.get(i - 1).getClose(),5,BigDecimal.ROUND_HALF_UP).doubleValue() > 0.05) {
                    set.add(entity.getKey().toString());
                }
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","YANGLINE",stockInfo);
        Long endTime = System.currentTimeMillis();
        logger.info("股票阳线计算结束:耗时：" + (endTime - startTime)/1000);
    }

    // 长下影线
    @Override
    public void longUnderLine() {
        logger.info("开始计算长下影线");
        Long startTime = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(3);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry entity:map.entrySet()){
            if(set.contains(entity.getKey().toString())){
                continue;
            }
            List<StockData> stockDataList = (List)entity.getValue();
            for(StockData stockData:stockDataList){
                if(stockData.getLow().compareTo(new BigDecimal(Math.max(stockData.getOpen().doubleValue(),stockData.getClose().doubleValue())* 0.97)) < 0 &&
                stockData.getHigh().compareTo(new BigDecimal(Math.max(stockData.getOpen().doubleValue(),stockData.getClose().doubleValue())*1.01)) < 0){
                    set.add(entity.getKey().toString());
                }
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","LONGUNDERLINE",stockInfo);
        Long endTime = System.currentTimeMillis();
        logger.info("股票长下影线计算结束:耗时：" + (endTime - startTime)/1000);
    }

    // 锤子线
    @Override
    public void hammerLine() {
        logger.info("开始计算锤子线");
        Long startTime = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(3);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry entity:map.entrySet()) {
            if (set.contains(entity.getKey().toString())) {
                continue;
            }
            List<StockData> stockDataList = (List)entity.getValue();
            for(StockData stockData:stockDataList){
                BigDecimal result = stockData.getClose().subtract(stockData.getOpen());
                if(result.compareTo(new BigDecimal(0)) == 0){
                    result = new BigDecimal(0.01);
                }
                BigDecimal absNum = new BigDecimal(Math.abs(result.doubleValue()));
                BigDecimal min = new BigDecimal(Math.min(stockData.getOpen().doubleValue(),stockData.getClose().doubleValue()));
                if((min.subtract(stockData.getLow())).divide(absNum,5,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(2)) > 0 &&
                stockData.getLow().compareTo(min.multiply(new BigDecimal(0.98))) < 0){
                    set.add(entity.getKey().toString());
                }
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","HAMMERLINE",stockInfo);
        Long endTime = System.currentTimeMillis();
        logger.info("股票锤子线计算结束:耗时：" + (endTime - startTime)/1000);
    }

    @Override
    public void KDJ() {
        logger.info("开始计算KDJ");
        Long startTime = System.currentTimeMillis();
        Set<String> glodSet = new HashSet<>();
        Set<String> DeadSet = new HashSet<>();
        Map<String,Object> map = getStockDatas(10);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry entity:map.entrySet()) {
            if (glodSet.contains(entity.getKey().toString()) && DeadSet.contains(entity.getKey().toString())) {
                continue;
            }
            List<StockData> stockDataList = (List) entity.getValue();
            List<StockData> stockDataToday = stockDataList.subList(1,10);
            List<StockData> stockDataBefore = stockDataList.subList(0,9);
            Collections.sort(stockDataToday, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getHigh().compareTo(o2.getHigh());
                }
            });
            Collections.sort(stockDataBefore, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getHigh().compareTo(o2.getHigh());
                }
            });
            // 获取9日内最高价
            BigDecimal todayHigh = stockDataToday.get(stockDataToday.size() - 1).getHigh();
            BigDecimal beforeHigh = stockDataBefore.get(stockDataBefore.size() - 1).getHigh();
            Collections.sort(stockDataToday, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getLow().compareTo(o2.getLow());
                }
            });
            Collections.sort(stockDataBefore, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getLow().compareTo(o2.getLow());
                }
            });
            // 获取9日内最低价
            BigDecimal todayLow = stockDataToday.get(0).getLow();
            BigDecimal beforeLow = stockDataBefore.get(0).getLow();
            Collections.sort(stockDataToday, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getTradingDay().compareTo(o2.getTradingDay());
                }
            });
            Collections.sort(stockDataBefore, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getTradingDay().compareTo(o2.getTradingDay());
                }
            });
            BigDecimal beforeK = null;
            BigDecimal beforeD = null;
            BigDecimal todayK = null;
            BigDecimal todayD = null;
            BigDecimal todayRSV = (stockDataToday.get(stockDataToday.size() - 1).getClose().subtract(todayLow)).divide(todayHigh.subtract(todayLow),5,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            if(redisUtil.getMap("KDJ",entity.getKey().toString()) == null) {
                // 上一日的RSV，计算上一日的K和D
                BigDecimal beforeRSV = (stockDataBefore.get(stockDataBefore.size() - 1).getClose().subtract(beforeLow)).divide(beforeHigh.subtract(beforeLow),5,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                beforeK = (beforeRSV.add(new BigDecimal(100))).divide(new BigDecimal(3),5,BigDecimal.ROUND_HALF_UP);
                beforeD = (beforeK.add(new BigDecimal(100))).divide(new BigDecimal(3),5,BigDecimal.ROUND_HALF_UP);
            }else{
                String value = redisUtil.getMap("KDJ",entity.getKey().toString());
                beforeK = new BigDecimal(value.split("_")[0]);
                beforeD = new BigDecimal(value.split("_")[1]);
            }
            todayK = (todayRSV.add(beforeK.multiply(new BigDecimal(2)))).divide(new BigDecimal(3),5,BigDecimal.ROUND_HALF_UP);
            todayD = (todayK.add(beforeD.multiply(new BigDecimal(2)))).divide(new BigDecimal(3),5,BigDecimal.ROUND_HALF_UP);
            BigDecimal todayJ = todayK.multiply(new BigDecimal(3)).subtract(todayD.multiply(new BigDecimal(2)));
            redisUtil.addMap("KDJ",entity.getKey().toString(),todayK + "_" + todayD + "_" + todayJ);
            // 金叉
            if(beforeK.compareTo(new BigDecimal(20)) < 0 &&
              beforeK.compareTo(beforeD) < 0 &&
                todayK.compareTo(todayD) > 0){
                glodSet.add(entity.getKey().toString());
                continue;
            }
            // 死叉
            if(beforeK.compareTo(new BigDecimal(80)) > 0 &&
              beforeK.compareTo(beforeD) < 0 &&
              todayK.compareTo(todayD) < 0){
                DeadSet.add(entity.getKey().toString());
            }
        }
        Long endTime = System.currentTimeMillis();
        logger.info("股票KDJ计算结束:耗时：" + (endTime - startTime)/1000);
        List<Map<String,Object>> goldStock = stockDataSelfMapper.selectStocksByList(glodSet,sdf.format(new Date()));
        for(Map<String,Object> entity:goldStock){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        List<Map<String,Object>> deadStock = stockDataSelfMapper.selectStocksByList(DeadSet,sdf.format(new Date()));
        for(Map<String,Object> entity:deadStock){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","GOLDCROSS",goldStock);
        redisUtil.addMap("CALC","DEADCROSS",deadStock);
    }

    @Override
    public void MACD() {
        logger.info("开始计算MACD");
        Long startTime = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(2);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            if (set.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            BigDecimal EMA12;
            BigDecimal EMA26;
            if(redisUtil.getMap("EMA",current.getKey().toString()) == null) {
                EMA12 = dataList.get(1).getClose().add((dataList.get(0).getClose().subtract(dataList.get(1).getClose())).multiply(new BigDecimal(2)).divide(new BigDecimal(13), 5, BigDecimal.ROUND_HALF_UP));
                EMA26 = dataList.get(1).getClose().add((dataList.get(0).getClose().subtract(dataList.get(1).getClose())).multiply(new BigDecimal(2)).divide(new BigDecimal(27), 5, BigDecimal.ROUND_HALF_UP));
            }else{
                EMA12 = new BigDecimal(redisUtil.getMap("EMA",current.getKey().toString()).split("_")[0]).multiply(new BigDecimal(11)).divide(new BigDecimal(13),5,BigDecimal.ROUND_HALF_UP).add(dataList.get(0).getClose().multiply(new BigDecimal(2).divide(new BigDecimal(13),5,BigDecimal.ROUND_HALF_UP)));
                EMA26 = new BigDecimal(redisUtil.getMap("EMA",current.getKey().toString()).split("_")[1]).multiply(new BigDecimal(25)).divide(new BigDecimal(27),5,BigDecimal.ROUND_HALF_UP).add(dataList.get(0).getClose().multiply(new BigDecimal(2).divide(new BigDecimal(27),5,BigDecimal.ROUND_HALF_UP)));
            }
            redisUtil.addMap("EMA",current.getKey().toString(),EMA12 + "_" + EMA26);
            BigDecimal DIFF = EMA12.subtract(EMA26);
            BigDecimal beforeDEA ;
            BigDecimal beforeMACD = new BigDecimal(0);
            if(redisUtil.getMap("MACD",current.getKey().toString()) == null) {
                beforeDEA = new BigDecimal(0);
            }else{
                String result = redisUtil.getMap("MACD",current.getKey().toString());
                beforeDEA = new BigDecimal(result.split("_")[1]);
                beforeMACD = new BigDecimal(redisUtil.getMap("MACD",current.getKey().toString()).split("_")[2]);
            }
            BigDecimal DEA = beforeDEA.multiply(new BigDecimal(0.8)).add(DIFF.multiply(new BigDecimal(0.2))).setScale(4,BigDecimal.ROUND_HALF_UP);
            BigDecimal MACD = (DIFF.subtract(DEA)).multiply(new BigDecimal(2)).setScale(4,BigDecimal.ROUND_HALF_UP);
            redisUtil.addMap("MACD",current.getKey().toString(),DIFF + "_" + DEA + "_" + MACD);
            if(beforeMACD.compareTo(new BigDecimal(0)) < 0 && MACD.compareTo(new BigDecimal(0)) > 0){
                set.add(current.getKey().toString());
            }
        }
        List<Map<String,Object>> MACDList = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:MACDList){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","MACD",MACDList);
        Long endTime = System.currentTimeMillis();
        logger.info("股票MACD计算结束:耗时：" + (endTime - startTime)/1000);
    }

    @Override
    public void BOLL() {
        logger.info("开始计算BOLL");
        Long startTime = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(20);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            if (set.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            Collections.sort(dataList, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return sdf.parse(o1.getTradingDay(),new ParsePosition(0)).before(sdf.parse(o2.getTradingDay(),new ParsePosition(0))) ? -1: 1;
                }
            });
            BigDecimal sum = new BigDecimal(0);
            for(StockData stockData:dataList){
                sum = sum.add(stockData.getClose());
            }
            BigDecimal MA = sum.divide(new BigDecimal(20),5,BigDecimal.ROUND_HALF_UP);
            // 中线
            BigDecimal MB = (sum.subtract(dataList.get(dataList.size()-1).getClose())).divide(new BigDecimal(19),5,BigDecimal.ROUND_HALF_UP);
            BigDecimal MDSUM = new BigDecimal(0);
            for(StockData stockData:dataList){
                MDSUM = MDSUM.add(new BigDecimal(Math.pow(stockData.getClose().subtract(MA).doubleValue(),2)));
            }
            BigDecimal MD = new BigDecimal(Math.sqrt(MDSUM.divide(new BigDecimal(20),5,BigDecimal.ROUND_HALF_UP).doubleValue()));
            // 上线
            BigDecimal UP = MB.add(MD.multiply(new BigDecimal(2)));
            // 下线
            BigDecimal DN = MB.subtract(MD.multiply(new BigDecimal(2)));
            if(dataList.get(dataList.size() - 2).getClose().compareTo(MB) < 0 &&
            dataList.get(dataList.size() - 1).getClose().compareTo(MB) > 0 &&
                    (UP.subtract(DN)).multiply(new BigDecimal(100)).divide(MB,4,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(10)) < 0){
                set.add(current.getKey().toString());
            }
            //redisUtil.addMap("BOLL",current.getKey().toString(),MA + "_" + MB + "_" + UP + "_" +DN);
        }
        List<Map<String, Object>> BOLLList = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:BOLLList){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        // 稳健买点
        redisUtil.addMap("CALC", "BOLL", BOLLList);
        Long endTime = System.currentTimeMillis();
        logger.info("股票BOLL计算结束:耗时：" + (endTime - startTime)/1000);
    }

    @Override
    public void WR() {
        Long start = System.currentTimeMillis();
        logger.info("开始计算WR");
        Set<String> RADICALBUYSet = new HashSet<>();
        Set<String> ROBUSTBUYSet = new HashSet<>();
        Map<String,Object> map = getStockDatas(29);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            BigDecimal TodayWR28 = new BigDecimal(0);
            BigDecimal beforeWR28 = new BigDecimal(0);
            BigDecimal TodayWR14 = new BigDecimal(0);
            BigDecimal beforeWR14 = new BigDecimal(0);
            if (RADICALBUYSet.contains(current.getKey().toString()) && ROBUSTBUYSet.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            if(dataList.size() < 29){
                continue;
            }
            Collections.sort(dataList, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return sdf.parse(o1.getTradingDay(),new ParsePosition(0)).before(sdf.parse(o2.getTradingDay(),new ParsePosition(0))) ? -1: 1;
                }
            });
            List<StockData> twentyList = dataList.subList(9,29);
            BigDecimal todayClose = twentyList.get(twentyList.size()-1).getClose();
            BigDecimal twentySum = new BigDecimal(0);
            for(StockData stockData:twentyList){
                twentySum = twentySum.add(stockData.getClose());
            }
            if(todayClose.compareTo(twentySum.divide(new BigDecimal(20),5,BigDecimal.ROUND_HALF_UP)) > 0){
                continue;
            }
            List<StockData> todayList = dataList.subList(1,29);
            BigDecimal closeOne = todayList.get(todayList.size()-1).getClose();
            List<StockData> beforeList = dataList.subList(0,28);
            BigDecimal closeTwo = beforeList.get(beforeList.size()-1).getClose();
            List<StockData> today14List = dataList.subList(15,29);
            BigDecimal closeThree = today14List.get(today14List.size()-1).getClose();
            List<StockData> before14List = dataList.subList(14,28);
            BigDecimal closeFour = before14List.get(before14List.size()-1).getClose();
            TodayWR28 = calcWR(todayList,closeOne);
            beforeWR28 = calcWR(beforeList,closeTwo);
            TodayWR14 = calcWR(today14List,closeThree);
            beforeWR14 = calcWR(before14List,closeFour);
            if(beforeWR14.add(beforeWR28).compareTo(new BigDecimal(50)) > 0 &&
            TodayWR14.add(TodayWR28).compareTo(new BigDecimal(50)) < 0){
                RADICALBUYSet.add(current.getKey().toString());
            }
            if(beforeWR14.add(beforeWR28).compareTo(new BigDecimal(20)) > 0 &&
                    beforeWR14.add(beforeWR28).compareTo(new BigDecimal(100)) < 0 &&
                    TodayWR14.add(TodayWR28).compareTo(new BigDecimal(20)) < 0){
                ROBUSTBUYSet.add(current.getKey().toString());
            }
        }
        if(RADICALBUYSet.size() > 0) {
            List<Map<String, Object>> RADICALBUY = stockDataSelfMapper.selectStocksByList(RADICALBUYSet,sdf.format(new Date()));
            for(Map<String,Object> entity:RADICALBUY){
                BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
                entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
            }
            // 激进买点
            redisUtil.addMap("CALC", "RADICALBUY", RADICALBUY);
        }
        if(ROBUSTBUYSet.size() > 0) {
            List<Map<String, Object>> ROBUSTBUY = stockDataSelfMapper.selectStocksByList(ROBUSTBUYSet,sdf.format(new Date()));
            for(Map<String,Object> entity:ROBUSTBUY){
                BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
                entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
            }
            // 稳健买点
            redisUtil.addMap("CALC", "ROBUSTBUY", ROBUSTBUY);
        }
        Long end = System.currentTimeMillis();
        logger.info("计算WR完成,总耗时" + (end - start) / 1000);
    }

    protected BigDecimal calcWR(List<StockData> todayList,BigDecimal todayClose){
        Collections.sort(todayList, new Comparator<StockData>() {
            @Override
            public int compare(StockData o1, StockData o2) {
                return o1.getLow().compareTo(o2.getLow());
            }
        });
        BigDecimal minLow28 = todayList.get(0).getLow();
        Collections.sort(todayList, new Comparator<StockData>() {
            @Override
            public int compare(StockData o1, StockData o2) {
                return o1.getHigh().compareTo(o2.getHigh());
            }
        });
        BigDecimal maxHigh28 = todayList.get(todayList.size()-1).getHigh();
        return new BigDecimal(100).subtract((todayClose.subtract(minLow28)).divide((maxHigh28.subtract(minLow28)),5,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    }

    @Override
    public void V() {
        Long start = System.currentTimeMillis();
        logger.info("开始计算V");
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(60);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            if (set.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> SixtyList = (List) current.getValue();
            List<StockData> FiveList = SixtyList.subList(55,60);
            List<StockData> TenList = SixtyList.subList(50,60);
            List<StockData> TenwtyList = SixtyList.subList(40,60);
            List<StockData> ThreeList = SixtyList.subList(57,60);
            BigDecimal MA60 = calcMA(SixtyList);
            BigDecimal MA5 = calcMA(FiveList);
            BigDecimal MA10 = calcMA(TenList);
            BigDecimal MA20 = calcMA(TenwtyList);
            BigDecimal MA3 = calcMA(ThreeList);
            if(MA5.compareTo(MA10) < 0 && MA10.compareTo(MA20) < 0 && MA20.compareTo(MA60) < 0 &&
              MA5.compareTo(MA60.multiply(new BigDecimal(0.8))) < 0 && MA3.compareTo(MA5) > 0){
                set.add(current.getKey().toString());
            }
        }
        List<Map<String,Object>> V = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:V){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","V",V);
        Long end = System.currentTimeMillis();
        logger.info("计算V结束，总耗时" + (end - start) / 1000);
    }

    protected  BigDecimal calcMA(List<StockData> stockData){
        BigDecimal sum = new BigDecimal(0);
        for(StockData entity:stockData){
            sum = sum.add(entity.getClose());
        }
        return sum.divide(new BigDecimal(stockData.size()),5,BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void SEA() {
        Long start = System.currentTimeMillis();
        logger.info("开始计算海底捞月");
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(60);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            if (set.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            List<StockData> fiveList = dataList.subList(55,60);
            List<StockData> beforeList = dataList.subList(54,59);
            Collections.sort(fiveList, new Comparator<StockData>() {
                @Override
                public int compare(StockData o1, StockData o2) {
                    return o1.getLow().compareTo(o2.getLow());
                }
            });
            BigDecimal LLV5 = fiveList.get(0).getLow();
            BigDecimal LLV60 = dataList.get(0).getLow();
            BigDecimal SUM60 = new BigDecimal(0);
            BigDecimal beforeSum = new BigDecimal(0);
            for(StockData stockData:dataList){
                if(LLV60.compareTo(stockData.getLow()) > 0){
                    LLV60 = stockData.getLow();
                }
                SUM60 = SUM60.add(stockData.getClose());
            }
            for(StockData stockData:beforeList){
                beforeSum = beforeSum.add(stockData.getClose());
            }
            if(LLV5.compareTo(LLV60) == 0 && LLV5.compareTo(SUM60.multiply(new BigDecimal(0.8))) < 0
              && dataList.get(dataList.size()-1).getClose().compareTo(beforeSum.divide(new BigDecimal(5),5,BigDecimal.ROUND_HALF_UP)) > 0){
                set.add(current.getKey().toString());
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","SEA",stockInfo);
        Long end = System.currentTimeMillis();
        logger.info("海底捞月计算完成，总耗时：" + (end - start) /1000);
    }

    @Override
    public void MORE() {
        Long start = System.currentTimeMillis();
        logger.info("开始计算均线多头");
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(21);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        for(Map.Entry current:map.entrySet()) {
            if (set.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            List<StockData> nowFive = dataList.subList(16,21);
            List<StockData> beforeFive = dataList.subList(15,20);
            List<StockData> nowTen = dataList.subList(11,21);
            List<StockData> beforeTen = dataList.subList(10,20);
            List<StockData> nowTwenty = dataList.subList(1,21);
            List<StockData> beforeTwenty = dataList.subList(0,20);
            BigDecimal nowMA5 = calcMA(nowFive);
            BigDecimal beforeMA5 = calcMA(beforeFive);
            BigDecimal nowMA10 = calcMA(nowTen);
            BigDecimal beforeMA10 = calcMA(beforeTen);
            BigDecimal nowMA20 = calcMA(nowTwenty);
            BigDecimal beforeMA20 = calcMA(beforeTwenty);
            if(nowMA5.compareTo(nowMA10) > 0 && nowMA10.compareTo(nowMA20) > 0 &&
                nowMA5.compareTo(beforeMA5) > 0 && nowMA10.compareTo(beforeMA10) > 0 &&
             nowMA20.compareTo(beforeMA20) > 0){
                set.add(current.getKey().toString());
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","MORE",stockInfo);
        Long end = System.currentTimeMillis();
        logger.info("计算多头均线结束，总耗时：" + (end - start) / 1000);
    }

    @Override
    public void THREEARMY() {
        Long start = System.currentTimeMillis();
        logger.info("开始计算三红兵");
        Set<String> set = new HashSet<>();
        Map<String,Object> map = getStockDatas(60);
        if(map == null){
            logger.info("股票数据不足");
            return;
        }
        loop:
        for(Map.Entry current:map.entrySet()) {
            if (set.contains(current.getKey().toString())) {
                continue;
            }
            List<StockData> dataList = (List) current.getValue();
            List<StockData> twentyList = dataList.subList(40,60);
            List<StockData> threeList = dataList.subList(57,60);
            BigDecimal minClose = threeList.get(0).getClose();
            for(StockData stockData:threeList){
                // 如果任意一个开盘价小于等于收盘价就不符合条件
                if(stockData.getClose().compareTo(stockData.getOpen()) <= 0){
                    continue loop;
                }
                // 找到3天以内的收盘最高价
                if(minClose.compareTo(stockData.getClose()) < 0){
                    minClose = stockData.getClose();
                }
                // 如果任意一个涨幅超过6%也不符合要求
                if((stockData.getClose().subtract(stockData.getOpen())).divide(stockData.getOpen(),5,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0.06)) > 0){
                    continue loop;
                }
            }
            BigDecimal averageTwenty = calcMA(twentyList);
            BigDecimal averageSixty = calcMA(dataList);
            // 用3天中的最低价的最高价和20日以及60日均线比较
            if(minClose.compareTo(averageSixty) < 0 && minClose.compareTo(averageTwenty) < 0){
                set.add(current.getKey().toString());
            }
        }
        List<Map<String,Object>> stockInfo = stockDataSelfMapper.selectStocksByList(set,sdf.format(new Date()));
        for(Map<String,Object> entity:stockInfo){
            BigDecimal frontClose = stockDataSelfMapper.selectFrontClose((Long)entity.get("id"),sdf.format(new Date()));
            entity.put("percent",(new BigDecimal(entity.get("close") + "").subtract(frontClose)).multiply(new BigDecimal(100)).divide(frontClose,2,BigDecimal.ROUND_HALF_UP) + "%");
        }
        redisUtil.addMap("CALC","THREEARMY",stockInfo);
        Long end = System.currentTimeMillis();
        logger.info("计算三红兵结束，总耗时：" + (end - start) / 1000);
    }

    public boolean calcToday(){
        List<String> dateList = new Gson().fromJson(redisUtil.getMap("common","trading_date"), List.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        // 说明今天不是交易日，就不用拉取数据
        if(!date.equals(dateList.get(dateList.size() - 1))){
            return false;
        }
        return true;
    }
}
