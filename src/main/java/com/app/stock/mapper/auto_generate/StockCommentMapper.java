package com.app.stock.mapper.auto_generate;

import com.app.stock.model.StockComment;
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

public interface StockCommentMapper {
    @Delete({
        "delete from stock_comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into stock_comment (user_id, stock_code, ",
        "stock_market, content, ",
        "is_delete, create_time, ",
        "modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{stockCode,jdbcType=VARCHAR}, ",
        "#{stockMarket,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(StockComment record);

    @InsertProvider(type=StockCommentSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(StockComment record);

    @Select({
        "select",
        "id, user_id, stock_code, stock_market, content, is_delete, create_time, modify_time",
        "from stock_comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="stock_code", property="stockCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock_market", property="stockMarket", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    StockComment selectByPrimaryKey(Long id);

    @UpdateProvider(type=StockCommentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StockComment record);

    @Update({
        "update stock_comment",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "stock_code = #{stockCode,jdbcType=VARCHAR},",
          "stock_market = #{stockMarket,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StockComment record);
}