package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.CostDetail;

public class CostDetailSqlProvider {

    public String insertSelective(CostDetail record) {
        BEGIN();
        INSERT_INTO("cost_detail");
        
        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }
        
        if (record.getOrderNumber() != null) {
            VALUES("order_number", "#{orderNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getCost() != null) {
            VALUES("cost", "#{cost,jdbcType=DECIMAL}");
        }
        
        if (record.getSubjectId() != null) {
            VALUES("subject_id", "#{subjectId,jdbcType=BIGINT}");
        }
        
        if (record.getMonth() != null) {
            VALUES("month", "#{month,jdbcType=BIGINT}");
        }
        
        if (record.getType() != null) {
            VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getPayType() != null) {
            VALUES("pay_type", "#{payType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(CostDetail record) {
        BEGIN();
        UPDATE("cost_detail");
        
        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=BIGINT}");
        }
        
        if (record.getOrderNumber() != null) {
            SET("order_number = #{orderNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getCost() != null) {
            SET("cost = #{cost,jdbcType=DECIMAL}");
        }
        
        if (record.getSubjectId() != null) {
            SET("subject_id = #{subjectId,jdbcType=BIGINT}");
        }
        
        if (record.getMonth() != null) {
            SET("month = #{month,jdbcType=BIGINT}");
        }
        
        if (record.getType() != null) {
            SET("type = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getPayType() != null) {
            SET("pay_type = #{payType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=VARCHAR}");
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