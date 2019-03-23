package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.NewsMapper;
import com.app.stock.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author mingxin Liu
 * @Date 2019-03-24 00:54
 * @Description //News 自己实现的sql
 */
public interface NewsSelfMapper extends NewsMapper {

    @Insert({
            "<script>",
            "insert into news (id,pub_date,channel_name,tag,description,channel_id,nid,",
            "link,have_pic,title,image_url,source,create_time)",
            "values",
            "<foreach collection='list' item='entity' index='index' separator=','>",
            "(#{entity.id},#{entity.pubDate},#{entity.channelName},#{entity.tag},#{entity.description},#{entity.channelId},",
            "#{entity.nid},#{entity.link},#{entity.havePic},#{entity.title},#{entity.imageUrl},#{entity.source},#{entity.createTime})",
            "</foreach>",
            "</script>"
    })
    int batchInsert(@Param("list") List<News> list);
}
