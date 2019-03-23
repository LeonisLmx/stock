package com.app.stock.schedule;

import com.app.stock.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Author mingxin Liu
 * @Date 2019-03-24 01:14
 * @Description //新闻相关定时任务
 */
@Component
public class NewsSchedule {

    @Autowired
    private NewsService newsService;

    @Scheduled(cron = "0 0/30 *  * * ?")
    public void newsPull() throws UnsupportedEncodingException {
        newsService.pullNews();
    }
}
