package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.AccountBook;

public class AccountBookSqlProvider {

    public String insertSelective(AccountBook record) {
        BEGIN();
        INSERT_INTO("account_book");
        
        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }
        
        if (record.getStoreTypeId() != null) {
            VALUES("store_type_id", "#{storeTypeId,jdbcType=BIGINT}");
        }
        
        if (record.getSerialNumber() != null) {
            VALUES("serial_number", "#{serialNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getPayType() != null) {
            VALUES("pay_type", "#{payType,jdbcType=VARCHAR}");
        }
        
        if (record.getCost() != null) {
            VALUES("cost", "#{cost,jdbcType=DECIMAL}");
        }
        
        if (record.getContent() != null) {
            VALUES("content", "#{content,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(AccountBook record) {
        BEGIN();
        UPDATE("account_book");
        
        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=BIGINT}");
        }
        
        if (record.getStoreTypeId() != null) {
            SET("store_type_id = #{storeTypeId,jdbcType=BIGINT}");
        }
        
        if (record.getSerialNumber() != null) {
            SET("serial_number = #{serialNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getPayType() != null) {
            SET("pay_type = #{payType,jdbcType=VARCHAR}");
        }
        
        if (record.getCost() != null) {
            SET("cost = #{cost,jdbcType=DECIMAL}");
        }
        
        if (record.getContent() != null) {
            SET("content = #{content,jdbcType=VARCHAR}");
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