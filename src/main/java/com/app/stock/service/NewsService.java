package com.app.stock.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @Author mingxin Liu
 * @Date 2019-03-24 00:15
 * @Description //新闻相关接口
 */
public interface NewsService {

    // 新闻拉取
    public void pullNews() throws UnsupportedEncodingException;

    public List<Map<String,Object>> searchNews();
}
