package com.app.stock.controller;

import com.app.stock.common.CommonUtil;
import com.app.stock.common.Response;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author mingxin Liu
 * @Date 2019-03-09 14:58
 * @Description HTTP请求接口
 */
@RequestMapping("/api/http")
@RestController
public class HttpClinetController {

    @RequestMapping(value = "/test",method = {RequestMethod.GET,RequestMethod.POST})
    public Response testHttp() throws UnsupportedEncodingException {
        Long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String url = "http://route.showapi.com/1529-11";
        Map<String,String> map = new HashMap<>();
        map.put("name","万达");
        map.put("showapi_timestamp",sdf.format(new Date()));
        map.put("showapi_appid","88986");
        String str = "name万达showapi_appid88986showapi_timestamp" + map.get("showapi_timestamp") + "a5471c3d69da4989a48e7412425e4d2e";
        String sign= DigestUtils.md5Hex(str.getBytes("utf-8"));
        map.put("showapi_sign",sign);
        String result = CommonUtil.doPost(url,map);
        Map<String,Object> map1 = new Gson().fromJson(result,Map.class);
        System.out.println(map1);
        Long end = System.currentTimeMillis();
        System.out.println("花费时间：" + (end - start) + "ms");
        return Response.ok(map1,"请求成功");
    }
}
