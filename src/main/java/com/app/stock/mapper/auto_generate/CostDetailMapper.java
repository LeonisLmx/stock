package com.app.stock.mapper.auto_generate;

import com.app.stock.model.CostDetail;
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

public interface CostDetailMapper {
    @Delete({
        "delete from cost_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into cost_detail (user_id, order_number, ",
        "cost, subject_id, ",
        "month, type, pay_type, ",
        "status, is_delete, ",
        "create_time, modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{orderNumber,jdbcType=VARCHAR}, ",
        "#{cost,jdbcType=DECIMAL}, #{subjectId,jdbcType=BIGINT}, ",
        "#{month,jdbcType=BIGINT}, #{type,jdbcType=VARCHAR}, #{payType,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{isDelete,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(CostDetail record);

    @InsertProvider(type=CostDetailSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(CostDetail record);

    @Select({
        "select",
        "id, user_id, order_number, cost, subject_id, month, type, pay_type, status, ",
        "is_delete, create_time, modify_time",
        "from cost_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_number", property="orderNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="cost", property="cost", jdbcType=JdbcType.DECIMAL),
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT),
        @Result(column="month", property="month", jdbcType=JdbcType.BIGINT),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="pay_type", property="payType", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    CostDetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=CostDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CostDetail record);

    @Update({
        "update cost_detail",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "order_number = #{orderNumber,jdbcType=VARCHAR},",
          "cost = #{cost,jdbcType=DECIMAL},",
          "subject_id = #{subjectId,jdbcType=BIGINT},",
          "month = #{month,jdbcType=BIGINT},",
          "type = #{type,jdbcType=VARCHAR},",
          "pay_type = #{payType,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "is_delete = #{isDelete,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CostDetail record);
}