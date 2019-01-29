package com.app.stock.mapper.auto_generate;

import com.app.stock.model.User;
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

public interface UserMapper {
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user (nickname, phone, ",
        "password, address, ",
        "score, descriptions, ",
        "headerImg, token, ",
        "is_admin, is_shield, ",
        "is_delete, create_time, ",
        "modify_time)",
        "values (#{nickname,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{score,jdbcType=INTEGER}, #{descriptions,jdbcType=VARCHAR}, ",
        "#{headerimg,jdbcType=VARCHAR}, #{token,jdbcType=VARCHAR}, ",
        "#{isAdmin,jdbcType=INTEGER}, #{isShield,jdbcType=INTEGER}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(User record);

    @Select({
        "select",
        "id, nickname, phone, password, address, score, descriptions, headerImg, token, ",
        "is_admin, is_shield, is_delete, create_time, modify_time",
        "from user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
        @Result(column="descriptions", property="descriptions", jdbcType=JdbcType.VARCHAR),
        @Result(column="headerImg", property="headerimg", jdbcType=JdbcType.VARCHAR),
        @Result(column="token", property="token", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_admin", property="isAdmin", jdbcType=JdbcType.INTEGER),
        @Result(column="is_shield", property="isShield", jdbcType=JdbcType.INTEGER),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    User selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update user",
        "set nickname = #{nickname,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "score = #{score,jdbcType=INTEGER},",
          "descriptions = #{descriptions,jdbcType=VARCHAR},",
          "headerImg = #{headerimg,jdbcType=VARCHAR},",
          "token = #{token,jdbcType=VARCHAR},",
          "is_admin = #{isAdmin,jdbcType=INTEGER},",
          "is_shield = #{isShield,jdbcType=INTEGER},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);
}