package com.app.stock.mapper.auto_generate;

import com.app.stock.model.StockData;
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

public interface StockDataMapper {
    @Delete({
        "delete from stock_data",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into stock_data (stock_id, open, ",
        "close, high, low, ",
        "volume, trading_day, ",
        "is_delete, create_time, ",
        "modify_time)",
        "values (#{stockId,jdbcType=BIGINT}, #{open,jdbcType=DECIMAL}, ",
        "#{close,jdbcType=DECIMAL}, #{high,jdbcType=DECIMAL}, #{low,jdbcType=DECIMAL}, ",
        "#{volume,jdbcType=DECIMAL}, #{tradingDay,jdbcType=VARCHAR}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(StockData record);

    @InsertProvider(type=StockDataSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(StockData record);

    @Select({
        "select",
        "id, stock_id, open, close, high, low, volume, trading_day, is_delete, create_time, ",
        "modify_time",
        "from stock_data",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="stock_id", property="stockId", jdbcType=JdbcType.BIGINT),
        @Result(column="open", property="open", jdbcType=JdbcType.DECIMAL),
        @Result(column="close", property="close", jdbcType=JdbcType.DECIMAL),
        @Result(column="high", property="high", jdbcType=JdbcType.DECIMAL),
        @Result(column="low", property="low", jdbcType=JdbcType.DECIMAL),
        @Result(column="volume", property="volume", jdbcType=JdbcType.DECIMAL),
        @Result(column="trading_day", property="tradingDay", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    StockData selectByPrimaryKey(Long id);

    @UpdateProvider(type=StockDataSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StockData record);

    @Update({
        "update stock_data",
        "set stock_id = #{stockId,jdbcType=BIGINT},",
          "open = #{open,jdbcType=DECIMAL},",
          "close = #{close,jdbcType=DECIMAL},",
          "high = #{high,jdbcType=DECIMAL},",
          "low = #{low,jdbcType=DECIMAL},",
          "volume = #{volume,jdbcType=DECIMAL},",
          "trading_day = #{tradingDay,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StockData record);
}