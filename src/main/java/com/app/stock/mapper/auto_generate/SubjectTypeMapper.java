package com.app.stock.mapper.auto_generate;

import com.app.stock.model.SubjectType;
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

public interface SubjectTypeMapper {
    @Delete({
        "delete from subject_type",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into subject_type (name, is_delete, ",
        "create_time, modify_time)",
        "values (#{name,jdbcType=VARCHAR}, #{isDelete,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(SubjectType record);

    @InsertProvider(type=SubjectTypeSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(SubjectType record);

    @Select({
        "select",
        "id, name, is_delete, create_time, modify_time",
        "from subject_type",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    SubjectType selectByPrimaryKey(Long id);

    @UpdateProvider(type=SubjectTypeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SubjectType record);

    @Update({
        "update subject_type",
        "set name = #{name,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SubjectType record);
}