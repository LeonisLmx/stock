package com.app.stock.mapper.auto_generate;

import com.app.stock.model.UserVipDetail;
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

public interface UserVipDetailMapper {
    @Delete({
        "delete from user_vip_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_vip_detail (user_id, cost_detail_id, ",
        "month, is_delete, ",
        "create_time, failure_time, ",
        "modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{costDetailId,jdbcType=BIGINT}, ",
        "#{month,jdbcType=INTEGER}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{failureTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(UserVipDetail record);

    @InsertProvider(type=UserVipDetailSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(UserVipDetail record);

    @Select({
        "select",
        "id, user_id, cost_detail_id, month, is_delete, create_time, failure_time, modify_time",
        "from user_vip_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="cost_detail_id", property="costDetailId", jdbcType=JdbcType.BIGINT),
        @Result(column="month", property="month", jdbcType=JdbcType.INTEGER),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="failure_time", property="failureTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserVipDetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserVipDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserVipDetail record);

    @Update({
        "update user_vip_detail",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "cost_detail_id = #{costDetailId,jdbcType=BIGINT},",
          "month = #{month,jdbcType=INTEGER},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "failure_time = #{failureTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserVipDetail record);
}