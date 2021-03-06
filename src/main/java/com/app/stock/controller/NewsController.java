package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author mingxin Liu
 * @Date 2019-03-24 00:14g
 * @Description //新闻相关
 */
@RequestMapping("/api/news")
@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    // 拉取新闻
    @RequestMapping("pullNews")
    public Response pullNews() throws UnsupportedEncodingException {
        newsService.pullNews();
        return Response.ok("操作成功");
    }

    // 查询相关新闻信息
    @RequestMapping("/search")
    public Response searchNews(@RequestBody Map<String,Object> map){
        return Response.ok(newsService.searchNews(map),"操作成功");
    }
}
