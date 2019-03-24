package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.NewsMapper;
import com.app.stock.model.News;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Delete({
            "delete from news where create_time <= now() - INTERVAL '3' day"
    })
    int deleteExpireNews();

    @Select({
            "select id from news"
    })
    Set<String> selectAllIds();

    @Select({
            "<script>",
            "select id,date_format(pub_date,'%Y-%m-%d %h:%i:%s') as pub_date,channel_name,tag,description,channel_id,nid,link,",
            "have_pic,title,image_url,source,date_format(create_time,'%Y-%m-%d %h:%i:%s') as create_time,date_format(modify_time,'%Y-%m-%d %h:%i:%s') as modify_time",
            "from news where 1 = 1",
            "<if test=\"condition != null and condition != ''\">",
            "and (tag like '%${condition}%' or title like '%${condition}%')",
            "</if>",
            "<if test=\"startTime != null and startTime != ''\">",
            "and create_time &gt;= #{startTime}",
            "</if>",
            "<if test=\"endTime != null and endTime != ''\">",
            "and create_time &lt;= #{endTime}",
            "</if>",
            "order by create_time desc,id desc",
            "</script>"
    })
    List<Map<String,Object>> searchNewsByCondition(@Param("condition")Object condition,
                                                   @Param("startTime")String startTime,
                                                   @Param("endTime")String endTime);
}
