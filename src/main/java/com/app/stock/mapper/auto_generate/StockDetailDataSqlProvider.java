package com.app.stock.mapper.auto_generate;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.app.stock.model.StockDetailData;

public class StockDetailDataSqlProvider {

    public String insertSelective(StockDetailData record) {
        BEGIN();
        INSERT_INTO("stock_detail_data");
        
        if (record.getStockCode() != null) {
            VALUES("stock_code", "#{stockCode,jdbcType=VARCHAR}");
        }
        
        if (record.getStockName() != null) {
            VALUES("stock_name", "#{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getCurrentPrice() != null) {
            VALUES("current_price", "#{currentPrice,jdbcType=DOUBLE}");
        }
        
        if (record.getChg() != null) {
            VALUES("chg", "#{chg,jdbcType=VARCHAR}");
        }
        
        if (record.getChange() != null) {
            VALUES("change", "#{change,jdbcType=DECIMAL}");
        }
        
        if (record.getOpen() != null) {
            VALUES("open", "#{open,jdbcType=DECIMAL}");
        }
        
        if (record.getHighest() != null) {
            VALUES("highest", "#{highest,jdbcType=DECIMAL}");
        }
        
        if (record.getLowest() != null) {
            VALUES("lowest", "#{lowest,jdbcType=DECIMAL}");
        }
        
        if (record.getCurClose() != null) {
            VALUES("cur_close", "#{curClose,jdbcType=DECIMAL}");
        }
        
        if (record.getVolumn() != null) {
            VALUES("volumn", "#{volumn,jdbcType=VARCHAR}");
        }
        
        if (record.getTotalValue() != null) {
            VALUES("total_value", "#{totalValue,jdbcType=VARCHAR}");
        }
        
        if (record.getPeRatioTtm() != null) {
            VALUES("pe_ratio_ttm", "#{peRatioTtm,jdbcType=VARCHAR}");
        }
        
        if (record.getPeRatioLyr() != null) {
            VALUES("pe_ratio_lyr", "#{peRatioLyr,jdbcType=VARCHAR}");
        }
        
        if (record.getTotalCapital() != null) {
            VALUES("total_capital", "#{totalCapital,jdbcType=BIGINT}");
        }
        
        if (record.getTurnover() != null) {
            VALUES("turnover", "#{turnover,jdbcType=BIGINT}");
        }
        
        if (record.getSwing() != null) {
            VALUES("swing", "#{swing,jdbcType=VARCHAR}");
        }
        
        if (record.getTurnoverRate() != null) {
            VALUES("turnover_rate", "#{turnoverRate,jdbcType=VARCHAR}");
        }
        
        if (record.getMarketValue() != null) {
            VALUES("market_value", "#{marketValue,jdbcType=VARCHAR}");
        }
        
        if (record.getMainBuy() != null) {
            VALUES("main_buy", "#{mainBuy,jdbcType=VARCHAR}");
        }
        
        if (record.getPlate() != null) {
            VALUES("plate", "#{plate,jdbcType=VARCHAR}");
        }
        
        if (record.getBuyFive() != null) {
            VALUES("buy_five", "#{buyFive,jdbcType=VARCHAR}");
        }
        
        if (record.getBuyTen() != null) {
            VALUES("buy_ten", "#{buyTen,jdbcType=VARCHAR}");
        }
        
        if (record.getBugTwenty() != null) {
            VALUES("bug_twenty", "#{bugTwenty,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(StockDetailData record) {
        BEGIN();
        UPDATE("stock_detail_data");
        
        if (record.getStockCode() != null) {
            SET("stock_code = #{stockCode,jdbcType=VARCHAR}");
        }
        
        if (record.getStockName() != null) {
            SET("stock_name = #{stockName,jdbcType=VARCHAR}");
        }
        
        if (record.getCurrentPrice() != null) {
            SET("current_price = #{currentPrice,jdbcType=DOUBLE}");
        }
        
        if (record.getChg() != null) {
            SET("chg = #{chg,jdbcType=VARCHAR}");
        }
        
        if (record.getChange() != null) {
            SET("change = #{change,jdbcType=DECIMAL}");
        }
        
        if (record.getOpen() != null) {
            SET("open = #{open,jdbcType=DECIMAL}");
        }
        
        if (record.getHighest() != null) {
            SET("highest = #{highest,jdbcType=DECIMAL}");
        }
        
        if (record.getLowest() != null) {
            SET("lowest = #{lowest,jdbcType=DECIMAL}");
        }
        
        if (record.getCurClose() != null) {
            SET("cur_close = #{curClose,jdbcType=DECIMAL}");
        }
        
        if (record.getVolumn() != null) {
            SET("volumn = #{volumn,jdbcType=VARCHAR}");
        }
        
        if (record.getTotalValue() != null) {
            SET("total_value = #{totalValue,jdbcType=VARCHAR}");
        }
        
        if (record.getPeRatioTtm() != null) {
            SET("pe_ratio_ttm = #{peRatioTtm,jdbcType=VARCHAR}");
        }
        
        if (record.getPeRatioLyr() != null) {
            SET("pe_ratio_lyr = #{peRatioLyr,jdbcType=VARCHAR}");
        }
        
        if (record.getTotalCapital() != null) {
            SET("total_capital = #{totalCapital,jdbcType=BIGINT}");
        }
        
        if (record.getTurnover() != null) {
            SET("turnover = #{turnover,jdbcType=BIGINT}");
        }
        
        if (record.getSwing() != null) {
            SET("swing = #{swing,jdbcType=VARCHAR}");
        }
        
        if (record.getTurnoverRate() != null) {
            SET("turnover_rate = #{turnoverRate,jdbcType=VARCHAR}");
        }
        
        if (record.getMarketValue() != null) {
            SET("market_value = #{marketValue,jdbcType=VARCHAR}");
        }
        
        if (record.getMainBuy() != null) {
            SET("main_buy = #{mainBuy,jdbcType=VARCHAR}");
        }
        
        if (record.getPlate() != null) {
            SET("plate = #{plate,jdbcType=VARCHAR}");
        }
        
        if (record.getBuyFive() != null) {
            SET("buy_five = #{buyFive,jdbcType=VARCHAR}");
        }
        
        if (record.getBuyTen() != null) {
            SET("buy_ten = #{buyTen,jdbcType=VARCHAR}");
        }
        
        if (record.getBugTwenty() != null) {
            SET("bug_twenty = #{bugTwenty,jdbcType=VARCHAR}");
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