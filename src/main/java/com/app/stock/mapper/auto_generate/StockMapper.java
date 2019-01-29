package com.app.stock.mapper.auto_generate;

import com.app.stock.model.Stock;
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

public interface StockMapper {
    @Delete({
        "delete from stock",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into stock (stock_code, stock_name, ",
        "market)",
        "values (#{stockCode,jdbcType=VARCHAR}, #{stockName,jdbcType=VARCHAR}, ",
        "#{market,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Stock record);

    @InsertProvider(type=StockSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Stock record);

    @Select({
        "select",
        "id, stock_code, stock_name, market",
        "from stock",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="stock_code", property="stockCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock_name", property="stockName", jdbcType=JdbcType.VARCHAR),
        @Result(column="market", property="market", jdbcType=JdbcType.VARCHAR)
    })
    Stock selectByPrimaryKey(Long id);

    @UpdateProvider(type=StockSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Stock record);

    @Update({
        "update stock",
        "set stock_code = #{stockCode,jdbcType=VARCHAR},",
          "stock_name = #{stockName,jdbcType=VARCHAR},",
          "market = #{market,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Stock record);
}