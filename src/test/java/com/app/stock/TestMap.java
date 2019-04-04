package com.app.stock;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @Author mingxin Liu
 * @Date 2019-04-02 13:17
 * @Description //TODO
 */
public class TestMap {

    public void testMap(Map map){
        Object result = map.put("1","2");
        Object result2 = map.put("1","3");
        System.out.println(result);
        System.out.println(result2);
    }

    @Test
    public void moreThread() throws ExecutionException, InterruptedException {
        Map<String,String> map = new ConcurrentHashMap<>();
        Executor executor = Executors.newFixedThreadPool(10);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10;i++) {
                        map.put(i + "", UUID.randomUUID().toString());
                        map.replace(i + "","ceshi");
                    }
                }
            });
        Thread.sleep(1000);
        System.out.println(map.toString());
        System.out.println(map.size());
    }

    @Test
    public void printMap(){
        Long start = System.currentTimeMillis();
        Map<String,Object> map = new HashMap<>();
        for(int i=0;i<12;i++){
            map.put(i + "",UUID.randomUUID().toString());
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main(String[] args) {
        String response = "<?xml version='1.0' encoding='UTF-8'?><SendSmsResponse><Message>OK</Message><RequestId>BFD77D59-1433-46DC-AF5F-1B283D38B3B5</RequestId><Code>isv.BUSINESS_LIMIT_CONTROL</Code></SendSmsResponse>";
        String result = response.substring(response.indexOf("<Message>") + 9,response.indexOf("</Message>"));
        System.out.println(result);
    }
}