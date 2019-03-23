package com.app.stock.mapper.auto_generate;

import com.app.stock.model.AppVersion;
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

public interface AppVersionMapper {
    @Delete({
        "delete from app_version",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into app_version (version, desc, ",
        "type, link_url, create_time, ",
        "modify_time, is_delete)",
        "values (#{version,jdbcType=VARCHAR}, #{desc,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{linkUrl,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{isDelete,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(AppVersion record);

    @InsertProvider(type=AppVersionSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(AppVersion record);

    @Select({
        "select",
        "id, version, desc, type, link_url, create_time, modify_time, is_delete",
        "from app_version",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="link_url", property="linkUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER)
    })
    AppVersion selectByPrimaryKey(Long id);

    @UpdateProvider(type=AppVersionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AppVersion record);

    @Update({
        "update app_version",
        "set version = #{version,jdbcType=VARCHAR},",
          "desc = #{desc,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "link_url = #{linkUrl,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "is_delete = #{isDelete,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(AppVersion record);
}