package com.app.stock.mapper.auto_generate;

import com.app.stock.model.StockDetailData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface StockDetailDataMapper {
    @Delete({
        "delete from stock_detail_data",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into stock_detail_data (stock_code, stock_name, ",
        "current_price, chg, ",
        "change, open, highest, ",
        "lowest, cur_close, ",
        "volumn, total_value, ",
        "pe_ratio_ttm, pe_ratio_lyr, ",
        "total_capital, turnover, ",
        "swing, turnover_rate, ",
        "market_value, main_buy, ",
        "plate, buy_five, ",
        "buy_ten, bug_twenty, ",
        "is_delete, create_time, ",
        "modify_time)",
        "values (#{stockCode,jdbcType=VARCHAR}, #{stockName,jdbcType=VARCHAR}, ",
        "#{currentPrice,jdbcType=DOUBLE}, #{chg,jdbcType=VARCHAR}, ",
        "#{change,jdbcType=DECIMAL}, #{open,jdbcType=DECIMAL}, #{highest,jdbcType=DECIMAL}, ",
        "#{lowest,jdbcType=DECIMAL}, #{curClose,jdbcType=DECIMAL}, ",
        "#{volumn,jdbcType=VARCHAR}, #{totalValue,jdbcType=VARCHAR}, ",
        "#{peRatioTtm,jdbcType=VARCHAR}, #{peRatioLyr,jdbcType=VARCHAR}, ",
        "#{totalCapital,jdbcType=BIGINT}, #{turnover,jdbcType=BIGINT}, ",
        "#{swing,jdbcType=VARCHAR}, #{turnoverRate,jdbcType=VARCHAR}, ",
        "#{marketValue,jdbcType=VARCHAR}, #{mainBuy,jdbcType=VARCHAR}, ",
        "#{plate,jdbcType=VARCHAR}, #{buyFive,jdbcType=VARCHAR}, ",
        "#{buyTen,jdbcType=VARCHAR}, #{bugTwenty,jdbcType=VARCHAR}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(StockDetailData record);

    @InsertProvider(type=StockDetailDataSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(StockDetailData record);

    @Select({
        "select",
        "id, stock_code, stock_name, current_price, chg, change, open, highest, lowest, ",
        "cur_close, volumn, total_value, pe_ratio_ttm, pe_ratio_lyr, total_capital, turnover, ",
        "swing, turnover_rate, market_value, main_buy, plate, buy_five, buy_ten, bug_twenty, ",
        "is_delete, create_time, modify_time",
        "from stock_detail_data",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="stock_code", property="stockCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock_name", property="stockName", jdbcType=JdbcType.VARCHAR),
        @Result(column="current_price", property="currentPrice", jdbcType=JdbcType.DOUBLE),
        @Result(column="chg", property="chg", jdbcType=JdbcType.VARCHAR),
        @Result(column="change", property="change", jdbcType=JdbcType.DECIMAL),
        @Result(column="open", property="open", jdbcType=JdbcType.DECIMAL),
        @Result(column="highest", property="highest", jdbcType=JdbcType.DECIMAL),
        @Result(column="lowest", property="lowest", jdbcType=JdbcType.DECIMAL),
        @Result(column="cur_close", property="curClose", jdbcType=JdbcType.DECIMAL),
        @Result(column="volumn", property="volumn", jdbcType=JdbcType.VARCHAR),
        @Result(column="total_value", property="totalValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="pe_ratio_ttm", property="peRatioTtm", jdbcType=JdbcType.VARCHAR),
        @Result(column="pe_ratio_lyr", property="peRatioLyr", jdbcType=JdbcType.VARCHAR),
        @Result(column="total_capital", property="totalCapital", jdbcType=JdbcType.BIGINT),
        @Result(column="turnover", property="turnover", jdbcType=JdbcType.BIGINT),
        @Result(column="swing", property="swing", jdbcType=JdbcType.VARCHAR),
        @Result(column="turnover_rate", property="turnoverRate", jdbcType=JdbcType.VARCHAR),
        @Result(column="market_value", property="marketValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="main_buy", property="mainBuy", jdbcType=JdbcType.VARCHAR),
        @Result(column="plate", property="plate", jdbcType=JdbcType.VARCHAR),
        @Result(column="buy_five", property="buyFive", jdbcType=JdbcType.VARCHAR),
        @Result(column="buy_ten", property="buyTen", jdbcType=JdbcType.VARCHAR),
        @Result(column="bug_twenty", property="bugTwenty", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    StockDetailData selectByPrimaryKey(Long id);

    @UpdateProvider(type=StockDetailDataSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StockDetailData record);

    @Update({
        "update stock_detail_data",
        "set stock_code = #{stockCode,jdbcType=VARCHAR},",
          "stock_name = #{stockName,jdbcType=VARCHAR},",
          "current_price = #{currentPrice,jdbcType=DOUBLE},",
          "chg = #{chg,jdbcType=VARCHAR},",
          "change = #{change,jdbcType=DECIMAL},",
          "open = #{open,jdbcType=DECIMAL},",
          "highest = #{highest,jdbcType=DECIMAL},",
          "lowest = #{lowest,jdbcType=DECIMAL},",
          "cur_close = #{curClose,jdbcType=DECIMAL},",
          "volumn = #{volumn,jdbcType=VARCHAR},",
          "total_value = #{totalValue,jdbcType=VARCHAR},",
          "pe_ratio_ttm = #{peRatioTtm,jdbcType=VARCHAR},",
          "pe_ratio_lyr = #{peRatioLyr,jdbcType=VARCHAR},",
          "total_capital = #{totalCapital,jdbcType=BIGINT},",
          "turnover = #{turnover,jdbcType=BIGINT},",
          "swing = #{swing,jdbcType=VARCHAR},",
          "turnover_rate = #{turnoverRate,jdbcType=VARCHAR},",
          "market_value = #{marketValue,jdbcType=VARCHAR},",
          "main_buy = #{mainBuy,jdbcType=VARCHAR},",
          "plate = #{plate,jdbcType=VARCHAR},",
          "buy_five = #{buyFive,jdbcType=VARCHAR},",
          "buy_ten = #{buyTen,jdbcType=VARCHAR},",
          "bug_twenty = #{bugTwenty,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StockDetailData record);
}