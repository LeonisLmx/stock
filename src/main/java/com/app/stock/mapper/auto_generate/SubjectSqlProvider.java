package com.app.stock.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.Subject;

public class SubjectSqlProvider {

    public String insertSelective(Subject record) {
        BEGIN();
        INSERT_INTO("subject");
        
        if (record.getSubjectTypeId() != null) {
            VALUES("subject_type_id", "#{subjectTypeId,jdbcType=BIGINT}");
        }
        
        if (record.getTeacherId() != null) {
            VALUES("teacher_id", "#{teacherId,jdbcType=BIGINT}");
        }
        
        if (record.getTitle() != null) {
            VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            VALUES("content", "#{content,jdbcType=VARCHAR}");
        }
        
        if (record.getSmallImg() != null) {
            VALUES("small_img", "#{smallImg,jdbcType=VARCHAR}");
        }
        
        if (record.getDetail() != null) {
            VALUES("detail", "#{detail,jdbcType=VARCHAR}");
        }
        
        if (record.getCommonPrice() != null) {
            VALUES("common_price", "#{commonPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getVipPrice() != null) {
            VALUES("vip_price", "#{vipPrice,jdbcType=DECIMAL}");
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

    public String updateByPrimaryKeySelective(Subject record) {
        BEGIN();
        UPDATE("subject");
        
        if (record.getSubjectTypeId() != null) {
            SET("subject_type_id = #{subjectTypeId,jdbcType=BIGINT}");
        }
        
        if (record.getTeacherId() != null) {
            SET("teacher_id = #{teacherId,jdbcType=BIGINT}");
        }
        
        if (record.getTitle() != null) {
            SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            SET("content = #{content,jdbcType=VARCHAR}");
        }
        
        if (record.getSmallImg() != null) {
            SET("small_img = #{smallImg,jdbcType=VARCHAR}");
        }
        
        if (record.getDetail() != null) {
            SET("detail = #{detail,jdbcType=VARCHAR}");
        }
        
        if (record.getCommonPrice() != null) {
            SET("common_price = #{commonPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getVipPrice() != null) {
            SET("vip_price = #{vipPrice,jdbcType=DECIMAL}");
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