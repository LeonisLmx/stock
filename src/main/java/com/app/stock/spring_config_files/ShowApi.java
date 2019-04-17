package com.app.stock.spring_config_files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @Author mingxin Liu
 * @Date 2019-03-24 10:17
 * @Description //showApi 相关的获取第三方api的配置
 */
@Component
public class ShowApi {

    @Value("${showapi.key}")
    private String key;

    @Value("${showapi.appId}")
    private String appId;

    @Value("${showapi.news.url}")
    private String newsUrl;

    @Value("${showapi.news.channelId}")
    private String newsChannelId;

    @Value("${showapi.stock.url}")
    private String stockUrl;

    @Value("${showapi.stockData.url}")
    private String stockDataUrl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsChannelId() {
        return newsChannelId;
    }

    public void setNewsChannelId(String newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    public String getStockUrl() {
        return stockUrl;
    }

    public void setStockUrl(String stockUrl) {
        this.stockUrl = stockUrl;
    }

    public String getStockDataUrl() {
        return stockDataUrl;
    }

    public void setStockDataUrl(String stockDataUrl) {
        this.stockDataUrl = stockDataUrl;
    }
}
