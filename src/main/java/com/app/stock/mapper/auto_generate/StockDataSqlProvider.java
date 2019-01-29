package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.StockData;

public class StockDataSqlProvider {

    public String insertSelective(StockData record) {
        BEGIN();
        INSERT_INTO("stock_data");
        
        if (record.getStockId() != null) {
            VALUES("stock_id", "#{stockId,jdbcType=BIGINT}");
        }
        
        if (record.getOpen() != null) {
            VALUES("open", "#{open,jdbcType=DECIMAL}");
        }
        
        if (record.getClose() != null) {
            VALUES("close", "#{close,jdbcType=DECIMAL}");
        }
        
        if (record.getHigh() != null) {
            VALUES("high", "#{high,jdbcType=DECIMAL}");
        }
        
        if (record.getLow() != null) {
            VALUES("low", "#{low,jdbcType=DECIMAL}");
        }
        
        if (record.getVolume() != null) {
            VALUES("volume", "#{volume,jdbcType=DECIMAL}");
        }
        
        if (record.getTradingDay() != null) {
            VALUES("trading_day", "#{tradingDay,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(StockData record) {
        BEGIN();
        UPDATE("stock_data");
        
        if (record.getStockId() != null) {
            SET("stock_id = #{stockId,jdbcType=BIGINT}");
        }
        
        if (record.getOpen() != null) {
            SET("open = #{open,jdbcType=DECIMAL}");
        }
        
        if (record.getClose() != null) {
            SET("close = #{close,jdbcType=DECIMAL}");
        }
        
        if (record.getHigh() != null) {
            SET("high = #{high,jdbcType=DECIMAL}");
        }
        
        if (record.getLow() != null) {
            SET("low = #{low,jdbcType=DECIMAL}");
        }
        
        if (record.getVolume() != null) {
            SET("volume = #{volume,jdbcType=DECIMAL}");
        }
        
        if (record.getTradingDay() != null) {
            SET("trading_day = #{tradingDay,jdbcType=VARCHAR}");
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