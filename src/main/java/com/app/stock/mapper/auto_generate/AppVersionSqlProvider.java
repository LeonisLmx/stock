package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.AppVersion;

public class AppVersionSqlProvider {

    public String insertSelective(AppVersion record) {
        BEGIN();
        INSERT_INTO("app_version");
        
        if (record.getVersion() != null) {
            VALUES("version", "#{version,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        if (record.getLinkUrl() != null) {
            VALUES("link_url", "#{linkUrl,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(AppVersion record) {
        BEGIN();
        UPDATE("app_version");
        
        if (record.getVersion() != null) {
            SET("version = #{version,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            SET("type = #{type,jdbcType=INTEGER}");
        }
        
        if (record.getLinkUrl() != null) {
            SET("link_url = #{linkUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=INTEGER}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}