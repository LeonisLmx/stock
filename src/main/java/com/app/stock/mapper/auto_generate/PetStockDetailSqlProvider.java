package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.PetStockDetail;

public class PetStockDetailSqlProvider {

    public String insertSelective(PetStockDetail record) {
        BEGIN();
        INSERT_INTO("pet_stock_detail");
        
        if (record.getPetId() != null) {
            VALUES("pet_id", "#{petId,jdbcType=BIGINT}");
        }
        
        if (record.getStockName() != null) {
            VALUES("stock_name", "#{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getStockCode() != null) {
            VALUES("stock_code", "#{stockCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            VALUES("price", "#{price,jdbcType=DECIMAL}");
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

    public String updateByPrimaryKeySelective(PetStockDetail record) {
        BEGIN();
        UPDATE("pet_stock_detail");
        
        if (record.getPetId() != null) {
            SET("pet_id = #{petId,jdbcType=BIGINT}");
        }
        
        if (record.getStockName() != null) {
            SET("stock_name = #{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getStockCode() != null) {
            SET("stock_code = #{stockCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            SET("price = #{price,jdbcType=DECIMAL}");
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