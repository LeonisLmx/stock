package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;

import com.app.stock.model.News;

public class NewsSqlProvider {

    public String insertSelective(News record) {
        BEGIN();
        INSERT_INTO("news");
        
        if (record.getPubDate() != null) {
            VALUES("pub_date", "#{pubDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getChannelName() != null) {
            VALUES("channel_name", "#{channelName,jdbcType=VARCHAR}");
        }
        
        if (record.getTag() != null) {
            VALUES("tag", "#{tag,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getChannelId() != null) {
            VALUES("channel_id", "#{channelId,jdbcType=VARCHAR}");
        }
        
        if (record.getNid() != null) {
            VALUES("nid", "#{nid,jdbcType=VARCHAR}");
        }
        
        if (record.getLink() != null) {
            VALUES("link", "#{link,jdbcType=VARCHAR}");
        }
        
        if (record.getHavePic() != null) {
            VALUES("have_pic", "#{havePic,jdbcType=INTEGER}");
        }
        
        if (record.getTitle() != null) {
            VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getImageUrl() != null) {
            VALUES("image_url", "#{imageUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getSource() != null) {
            VALUES("source", "#{source,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=INTEGER}");
        }
        
        return SQL();
    }
}