package com.app.stock.mapper.auto_generate;

import com.app.stock.model.PetStockDetail;
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

public interface PetStockDetailMapper {
    @Delete({
        "delete from pet_stock_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pet_stock_detail (pet_id, stock_name, ",
        "stock_code, price, ",
        "is_delete, create_time, ",
        "modify_time)",
        "values (#{petId,jdbcType=BIGINT}, #{stockName,jdbcType=VARCHAR}, ",
        "#{stockCode,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(PetStockDetail record);

    @InsertProvider(type=PetStockDetailSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(PetStockDetail record);

    @Select({
        "select",
        "id, pet_id, stock_name, stock_code, price, is_delete, create_time, modify_time",
        "from pet_stock_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.BIGINT),
        @Result(column="stock_name", property="stockName", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock_code", property="stockCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PetStockDetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=PetStockDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PetStockDetail record);

    @Update({
        "update pet_stock_detail",
        "set pet_id = #{petId,jdbcType=BIGINT},",
          "stock_name = #{stockName,jdbcType=VARCHAR},",
          "stock_code = #{stockCode,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PetStockDetail record);
}