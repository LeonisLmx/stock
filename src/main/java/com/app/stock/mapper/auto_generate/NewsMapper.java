package com.app.stock.mapper.auto_generate;

import com.app.stock.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

public interface NewsMapper {
    @Insert({
        "insert into news (pub_date, channel_name, ",
        "tag, desc, channelId, ",
        "nid, link, have_pic, ",
        "title, imageurls, ",
        "source, create_time, ",
        "modify_time, is_delete)",
        "values (#{pubDate,jdbcType=TIMESTAMP}, #{channelName,jdbcType=VARCHAR}, ",
        "#{tag,jdbcType=VARCHAR}, #{desc,jdbcType=VARCHAR}, #{channelid,jdbcType=VARCHAR}, ",
        "#{nid,jdbcType=VARCHAR}, #{link,jdbcType=VARCHAR}, #{havePic,jdbcType=INTEGER}, ",
        "#{title,jdbcType=VARCHAR}, #{imageurls,jdbcType=VARCHAR}, ",
        "#{source,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{isDelete,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(News record);

    @InsertProvider(type=NewsSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(News record);
}