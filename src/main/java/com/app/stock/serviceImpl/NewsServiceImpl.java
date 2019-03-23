package com.app.stock.serviceImpl;

import com.app.stock.common.CommonUtil;
import com.app.stock.common.HttpClientRequest;
import com.app.stock.mapper.NewsSelfMapper;
import com.app.stock.model.News;
import com.app.stock.service.NewsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author mingxin Liu
 * @Date 2019-03-24 00:15
 * @Description //TODO
 */
@Service
public class NewsServiceImpl implements NewsService {

    private static Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    private static String URL = "http://route.showapi.com/109-35";
    private static String APPID = "88931";
    private static String KEY = "8e5cc51d121d4ca4ad0cbb496b42a01a";

    @Autowired
    private NewsSelfMapper newsSelfMapper;

    @Override
    public void pullNews() throws UnsupportedEncodingException {
        Long startTime = System.currentTimeMillis();
        Gson gson = new Gson();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String,Object> map = new HashMap<>();
        map.put("showapi_timestamp",sdf.format(new Date()));
        map.put("showapi_appid",APPID);
        map.put("channelId","5572a108b3cdc86cf39001d0");
        map.put("maxResult","100");
        StringBuilder sb = CommonUtil.sortMap(map);
        sb.append(KEY);
        String sign = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
        map.put("showapi_sign", sign);
        String response =  HttpClientRequest.doPost(URL,map);
        response = response.substring(response.indexOf("contentlist") + 13,response.lastIndexOf("]") + 1);
        List<Map<String,Object>> bigMap = gson.fromJson(response,new TypeToken<List>(){}.getType());
        List<News> list = new ArrayList<>();
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashSet<String> set = new HashSet();
        for(Map<String,Object> entity:bigMap){
            if(set.contains(entity.get("id").toString())){
                continue;
            }
            set.add(entity.get("id").toString());
            News news = new News();
            news.setPubDate(sdfTime.parse(entity.get("pubDate").toString(),new ParsePosition(0)));
            news.setChannelName(entity.get("channelName").toString());
            news.setDescription(entity.get("desc").toString());
            news.setChannelId(entity.get("channelId").toString());
            news.setNid(entity.get("nid") == null?null:entity.get("nid").toString());
            news.setLink(entity.get("link").toString());
            news.setId(entity.get("id").toString());
            news.setHavePic(((Boolean)entity.get("havePic"))?1:0);
            news.setTitle(entity.get("title").toString());
            news.setSource(entity.get("source").toString());
            news.setCreateTime(new Date());
            if(entity.get("feedback_tag") != null){
                StringBuilder sbTag = new StringBuilder("");
                List<Map<String,Object>> tagList = (List<Map<String,Object>>) entity.get("feedback_tag");
                tagList.forEach((v) -> {
                    sbTag.append(v.get("name")).append(",");
                });
                news.setTag(sbTag.toString().substring(0,sbTag.toString().length()-1));
            }
            if(news.getHavePic() == 1){
                List<Map<String,Object>> imageurls = (List<Map<String,Object>>) entity.get("imageurls");
                news.setImageUrl(imageurls.get(0).get("url").toString());
            }
            list.add(news);
        }
        newsSelfMapper.batchInsert(list);
        Long endTime = System.currentTimeMillis();
        logger.info("拉取新闻结束，总耗时：" + (endTime - startTime) / 1000);
    }

    @Override
    public List<Map<String, Object>> searchNews() {
        return null;
    }
}
