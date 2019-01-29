package com.app.stock.mapper.auto_generate;

import com.app.stock.model.Pet;
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

public interface PetMapper {
    @Delete({
        "delete from pet",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pet (user_id, name, ",
        "level, change_score, ",
        "percent, is_delete, ",
        "create_time, modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{level,jdbcType=INTEGER}, #{changeScore,jdbcType=INTEGER}, ",
        "#{percent,jdbcType=VARCHAR}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Pet record);

    @InsertProvider(type=PetSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Pet record);

    @Select({
        "select",
        "id, user_id, name, level, change_score, percent, is_delete, create_time, modify_time",
        "from pet",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="change_score", property="changeScore", jdbcType=JdbcType.INTEGER),
        @Result(column="percent", property="percent", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Pet selectByPrimaryKey(Long id);

    @UpdateProvider(type=PetSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Pet record);

    @Update({
        "update pet",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "level = #{level,jdbcType=INTEGER},",
          "change_score = #{changeScore,jdbcType=INTEGER},",
          "percent = #{percent,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Pet record);
}