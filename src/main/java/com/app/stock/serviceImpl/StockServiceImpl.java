package com.app.stock.serviceImpl;

import com.app.stock.common.CommonUtil;
import com.app.stock.common.HttpClientRequest;
import com.app.stock.config.redis.RedisExecutor;
import com.app.stock.mapper.StockDataSelfMapper;
import com.app.stock.mapper.StockSelfMapper;
import com.app.stock.model.Stock;
import com.app.stock.model.request.StockDetailRequest;
import com.app.stock.model.result.StockResult;
import com.app.stock.service.StockService;
import com.app.stock.spring_config_files.ShowApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

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

    @Autowired
    private ShowApi showApi;

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
            List<Map<String,Object>> redisList = new ArrayList<>();
            if(entity.equals("1")){
                redisList = gson.fromJson(redisExecutor.getMap("CALC","CROSS"),List.class);
            }else if(entity.equals("2")){
                redisList = gson.fromJson(redisExecutor.getMap("CALC","YANGLINE"),List.class);
            }else if(entity.equals("3")){
                redisList = gson.fromJson(redisExecutor.getMap("CALC","LONGUNDERLINE"),List.class);
            }else if(entity.equals("4")){
                redisList = gson.fromJson(redisExecutor.getMap("CALC","HAMMERLINE"),List.class);
            }else if(entity.equals("5")){
                // 金叉
                redisList = gson.fromJson(redisExecutor.getMap("CALC","GOLDCROSS"),List.class);
            }else if(entity.equals("6")){
                // 死叉
                redisList = gson.fromJson(redisExecutor.getMap("CALC","DEADCROSS"),List.class);
            }else  if(entity.equals("7")){
                // 稳健买点
                redisList = gson.fromJson(redisExecutor.getMap("CALC","ROBUSTBUY"),List.class);
            }else if(entity.equals("8")) {
                // 激进买点
                redisList = gson.fromJson(redisExecutor.getMap("CALC", "RADICALBUY"), List.class);
            }else if(entity.equals("9")){
                // BOLL
                redisList = gson.fromJson(redisExecutor.getMap("CALC","BOLL"),List.class);
            }else if(entity.equals("10")){
                // V字底
                redisList = gson.fromJson(redisExecutor.getMap("CALC","V"),List.class);
            }else if(entity.equals("11")){
                // 海底捞月
                redisList = gson.fromJson(redisExecutor.getMap("CALC","SEA"),List.class);
            }else if(entity.equals("12")){
                // 均线多头
                redisList = gson.fromJson(redisExecutor.getMap("CALC","MORE"),List.class);
            }else if(entity.equals("13")){
                // 红三兵
                redisList = gson.fromJson(redisExecutor.getMap("CALC","THREEARMY"),List.class);
            }else if(entity.equals("14")){
                // MACD
                redisList = gson.fromJson(redisExecutor.getMap("CALC","MACD"),List.class);
            }
            if(list == null || list.size() == 0){
                list = redisList;
            }else {
                list = addRepeatList(list, redisList);
            }
        }
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return o1.get("stock_code").toString().compareTo(o2.get("stock_code").toString());
            }
        });
        for(Map<String,Object> entity:list){
            entity.put("id",new Double(entity.get("id").toString()).intValue());
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getFormualData(Map<String, Object> map) {
        Integer num = Integer.valueOf(map.get("condition") + "");
        if(num < 5 || num > 12){
            return null;
        }
        List<Map<String,Object>> redisList = new ArrayList<>();
        Gson gson = new Gson();
        if(num == 5){
            // 金叉
            redisList = gson.fromJson(redisExecutor.getMap("CALC","GOLDCROSS"),List.class);
        }else if(num == 6){
            // 死叉
            redisList = gson.fromJson(redisExecutor.getMap("CALC","DEADCROSS"),List.class);
        }else  if(num == 7){
            // 稳健买点
            redisList = gson.fromJson(redisExecutor.getMap("CALC","ROBUSTBUY"),List.class);
        }else if(num == 8) {
            // 激进买点
            redisList = gson.fromJson(redisExecutor.getMap("CALC", "RADICALBUY"), List.class);
        }else if(num == 9){
            // BOLL
            redisList = gson.fromJson(redisExecutor.getMap("CALC","BOLL"),List.class);
        }else if(num == 10){
            // V字底
            redisList = gson.fromJson(redisExecutor.getMap("CALC","V"),List.class);
        }else if(num == 11){
            // 海底捞月
            redisList = gson.fromJson(redisExecutor.getMap("CALC","SEA"),List.class);
        }else if(num == 12){
            // 均线多头
            redisList = gson.fromJson(redisExecutor.getMap("CALC","MORE"),List.class);
        }else if(num == 13){
            // 红三兵
            redisList = gson.fromJson(redisExecutor.getMap("CALC","THREEARMY"),List.class);
        }
        if(redisList == null){
            return redisList;
        }
        Collections.sort(redisList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return o1.get("stock_code").toString().compareTo(o2.get("stock_code").toString());
            }
        });
        for(Map<String,Object> entity:redisList){
            entity.put("id",new Double(entity.get("id").toString()).intValue());
        }
        return redisList;
    }

    protected List<Map<String,Object>> addRepeatList(List<Map<String,Object>> originalList, List<Map<String,Object>> targetList){
        if(originalList == null || originalList.size() == 0){
            return null;
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Map<String,Object> entity:targetList){
            if(originalList.contains(entity)){
                resultList.add(entity);
            }
        }
        return resultList;
    }

    @Override
    public List<Object> getStockDetails(StockDetailRequest stockDetailRequest) throws UnsupportedEncodingException {
        List<String> stocks = stockDetailRequest.getStocks();
        List<String> stockCodes = stockSelfMapper.selectStockCodes(stocks);
        if(stockCodes.size() > 0) {
            String conditionStocks = stockCodes.toString().substring(1,stockCodes.toString().length()-1).replaceAll(" ","");
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("showapi_appid", showApi.getAppId());
            conditionMap.put("stocks", conditionStocks);
            System.out.println(conditionMap.get("stocks"));
            StringBuilder sb = CommonUtil.sortMap(conditionMap);
            sb.append(showApi.getKey());
            String sign = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
            conditionMap.put("showapi_sign", sign);
            String result = HttpClientRequest.doPost("http://route.showapi.com/131-46", conditionMap);
            StockResult stockResult = new Gson().fromJson(result, StockResult.class);
            System.out.println(result);
            logger.info("股票信息查询成功");
            return stockResult.getShowapi_res_body().getList();
        }else{
            logger.info("股票信息不存在");
            return null;
        }
    }

    // 获取新浪概念板块
    @Override
    public List<Object> getSinaData(Integer page, Integer page_size,Integer type,Integer asc) throws Exception {
        String conceptUrl = "http://gu.sina.cn/hq/api/openapi.php/StockV2Service.getPlateList?num=" + page_size + "&page=" + page +"&sort=percent&asc=" + asc + "&dpc=1&plate=chgn";
        String industryUrl = "http://gu.sina.cn/hq/api/openapi.php/StockV2Service.getPlateList?num=" + page_size + "&page="+ page +"&sort=percent&asc=" + asc + "&dpc=1&plate=sw2";
        String url = 1 == type? conceptUrl:industryUrl;
        String response =  HttpClientRequest.Get(url);
        response = response.substring(response.indexOf("["),response.indexOf("]") + 1);
        List<Object> list = new Gson().fromJson(response,List.class);
        return list;
    }

    @Override
    public void sychornizedStockData() throws UnsupportedEncodingException {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("showapi_appid", showApi.getAppId());
        conditionMap.put("market","sh");
        StringBuilder sb = CommonUtil.sortMap(conditionMap);
        sb.append(showApi.getKey());
        String sign = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
        conditionMap.put("showapi_sign", sign);
        String result = HttpClientRequest.doPost(showApi.getStockDataUrl(), conditionMap);
        Map<String,Object> map = new Gson().fromJson(result,Map.class);
        Integer pageTotal = Double.valueOf(((Map)map.get("showapi_res_body")).get("allPages").toString()).intValue();
        result = result.substring(result.indexOf("["),result.lastIndexOf("]") + 1);
        List<Map<String,Object>> list = new Gson().fromJson(result,new TypeToken<List>(){}.getType());
        insertIntoStocks(list);
        for(int i=2;i<pageTotal;i++){
            achieveData(conditionMap,i);
        }
    }

    protected void achieveData(Map<String,Object> conditionMap,int i) throws UnsupportedEncodingException {
        conditionMap.remove("showapi_sign");
        conditionMap.put("page",i);
        StringBuilder sb = CommonUtil.sortMap(conditionMap);
        sb.append(showApi.getKey());
        String sign = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
        conditionMap.put("showapi_sign", sign);
        String result = HttpClientRequest.doPost(showApi.getStockDataUrl(), conditionMap);
        result = result.substring(result.indexOf("["),result.lastIndexOf("]") + 1);
        List<Map<String,Object>> list = new Gson().fromJson(result,new TypeToken<List>(){}.getType());
        insertIntoStocks(list);
    }

    protected void insertIntoStocks(List<Map<String,Object>> list){
        List<Stock> stocks = new ArrayList<>();
        for(Map<String,Object> entity:list){
            Stock stock = new Stock();
            stock.setMarket(entity.get("market").equals("sz")?"zh":"sh");
            stock.setStockCode(entity.get("code").toString());
            stock.setStockName(entity.get("name").toString());
            stocks.add(stock);
        }
        stockSelfMapper.batchInsertStocks(stocks);
    }
}
