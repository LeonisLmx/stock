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
        
        if (record.getStockId() != null) {
            VALUES("stock_id", "#{stockId,jdbcType=BIGINT}");
        }
        
        if (record.getStockName() != null) {
            VALUES("stock_name", "#{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getbPrice() != null) {
            VALUES("b_price", "#{bPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getbTime() != null) {
            VALUES("b_time", "#{bTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getsPrice() != null) {
            VALUES("s_price", "#{sPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getsTime() != null) {
            VALUES("s_time", "#{sTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIncrease() != null) {
            VALUES("increase", "#{increase,jdbcType=DECIMAL}");
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
        
        if (record.getStockId() != null) {
            SET("stock_id = #{stockId,jdbcType=BIGINT}");
        }
        
        if (record.getStockName() != null) {
            SET("stock_name = #{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getbPrice() != null) {
            SET("b_price = #{bPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getbTime() != null) {
            SET("b_time = #{bTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getsPrice() != null) {
            SET("s_price = #{sPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getsTime() != null) {
            SET("s_time = #{sTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIncrease() != null) {
            SET("increase = #{increase,jdbcType=DECIMAL}");
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