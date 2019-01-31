package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.service.ScheduleCrossService;
import com.app.stock.service.StockService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by lmx
 * Date 2019/1/7
 */
@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ScheduleCrossService scheduleCrossService;

    @RequestMapping(value = "/getStock",method = {RequestMethod.GET,RequestMethod.POST})
    public Response getStock(@RequestBody Map<String,String> map){
        return Response.ok(stockService.selectAllByKeywords(map.get("keywords")),"操作成功");
    }

    @RequestMapping(value = "/getInfo",method = {RequestMethod.GET,RequestMethod.POST})
    public Response getInfo(@RequestBody Map<String,String> map){
        if(StringUtils.isBlank(map.get("stockCode")) || StringUtils.isBlank(map.get("market"))){
            return Response.ok(null,"股票代码或股票市场不能为空",1);
        }
        return Response.ok(stockService.selectInfoByCode(map.get("stockCode"),map.get("market")),"操作成功");
    }

    @RequestMapping("/yangLine")
    public Response yangLine(){
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    scheduleCrossService.pullingData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return Response.ok("操作成功");
    }
}