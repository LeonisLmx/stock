package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.SubjectDetail;

public class SubjectDetailSqlProvider {

    public String insertSelective(SubjectDetail record) {
        BEGIN();
        INSERT_INTO("subject_detail");
        
        if (record.getSubjectId() != null) {
            VALUES("subject_id", "#{subjectId,jdbcType=BIGINT}");
        }
        
        if (record.getVedioUrl() != null) {
            VALUES("vedio_url", "#{vedioUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getPlayCount() != null) {
            VALUES("play_count", "#{playCount,jdbcType=BIGINT}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(SubjectDetail record) {
        BEGIN();
        UPDATE("subject_detail");
        
        if (record.getSubjectId() != null) {
            SET("subject_id = #{subjectId,jdbcType=BIGINT}");
        }
        
        if (record.getVedioUrl() != null) {
            SET("vedio_url = #{vedioUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getPlayCount() != null) {
            SET("play_count = #{playCount,jdbcType=BIGINT}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}