package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.Stock;

public class StockSqlProvider {

    public String insertSelective(Stock record) {
        BEGIN();
        INSERT_INTO("stock");
        
        if (record.getStockCode() != null) {
            VALUES("stock_code", "#{stockCode,jdbcType=VARCHAR}");
        }
        
        if (record.getStockName() != null) {
            VALUES("stock_name", "#{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getMarket() != null) {
            VALUES("market", "#{market,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(Stock record) {
        BEGIN();
        UPDATE("stock");
        
        if (record.getStockCode() != null) {
            SET("stock_code = #{stockCode,jdbcType=VARCHAR}");
        }
        
        if (record.getStockName() != null) {
            SET("stock_name = #{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getMarket() != null) {
            SET("market = #{market,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}