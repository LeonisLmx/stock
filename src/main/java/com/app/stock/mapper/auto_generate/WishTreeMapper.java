package com.app.stock.mapper.auto_generate;

import com.app.stock.model.WishTree;
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

public interface WishTreeMapper {
    @Delete({
        "delete from wish_tree",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into wish_tree (user_id, wish_month, ",
        "is_delete, create_time, ",
        "modify_time, wish)",
        "values (#{userId,jdbcType=BIGINT}, #{wishMonth,jdbcType=VARCHAR}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{wish,jdbcType=LONGVARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(WishTree record);

    @InsertProvider(type=WishTreeSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(WishTree record);

    @Select({
        "select",
        "id, user_id, wish_month, is_delete, create_time, modify_time, wish",
        "from wish_tree",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="wish_month", property="wishMonth", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="wish", property="wish", jdbcType=JdbcType.LONGVARCHAR)
    })
    WishTree selectByPrimaryKey(Long id);

    @UpdateProvider(type=WishTreeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WishTree record);

    @Update({
        "update wish_tree",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "wish_month = #{wishMonth,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "wish = #{wish,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(WishTree record);

    @Update({
        "update wish_tree",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "wish_month = #{wishMonth,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(WishTree record);
}