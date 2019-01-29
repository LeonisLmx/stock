package com.app.stock.mapper.auto_generate;

import com.app.stock.model.Advertise;
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

public interface AdvertiseMapper {
    @Delete({
        "delete from advertise",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into advertise (title, image_url, ",
        "type, to_url, is_delete, ",
        "create_time, modify_time)",
        "values (#{title,jdbcType=VARCHAR}, #{imageUrl,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{toUrl,jdbcType=VARCHAR}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Advertise record);

    @InsertProvider(type=AdvertiseSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Advertise record);

    @Select({
        "select",
        "id, title, image_url, type, to_url, is_delete, create_time, modify_time",
        "from advertise",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="image_url", property="imageUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="to_url", property="toUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Advertise selectByPrimaryKey(Long id);

    @UpdateProvider(type=AdvertiseSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Advertise record);

    @Update({
        "update advertise",
        "set title = #{title,jdbcType=VARCHAR},",
          "image_url = #{imageUrl,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "to_url = #{toUrl,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Advertise record);
}