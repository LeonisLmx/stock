package com.app.stock.mapper.auto_generate;

import com.app.stock.model.UserSubjectDetail;
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

public interface UserSubjectDetailMapper {
    @Delete({
        "delete from user_subject_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_subject_detail (user_id, subject_id, ",
        "cost_detail_id, is_delete, ",
        "create_time, modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{subjectId,jdbcType=BIGINT}, ",
        "#{costDetailId,jdbcType=BIGINT}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(UserSubjectDetail record);

    @InsertProvider(type=UserSubjectDetailSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(UserSubjectDetail record);

    @Select({
        "select",
        "id, user_id, subject_id, cost_detail_id, is_delete, create_time, modify_time",
        "from user_subject_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT),
        @Result(column="cost_detail_id", property="costDetailId", jdbcType=JdbcType.BIGINT),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserSubjectDetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserSubjectDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserSubjectDetail record);

    @Update({
        "update user_subject_detail",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "subject_id = #{subjectId,jdbcType=BIGINT},",
          "cost_detail_id = #{costDetailId,jdbcType=BIGINT},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserSubjectDetail record);
}