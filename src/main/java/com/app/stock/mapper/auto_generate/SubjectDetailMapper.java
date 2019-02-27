package com.app.stock.mapper.auto_generate;

import com.app.stock.model.SubjectDetail;
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

public interface SubjectDetailMapper {
    @Delete({
        "delete from subject_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into subject_detail (subject_id, title, ",
        "duration, vedio_url, ",
        "play_count, is_delete, ",
        "create_time, modify_time)",
        "values (#{subjectId,jdbcType=BIGINT}, #{title,jdbcType=VARCHAR}, ",
        "#{duration,jdbcType=BIGINT}, #{vedioUrl,jdbcType=VARCHAR}, ",
        "#{playCount,jdbcType=BIGINT}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(SubjectDetail record);

    @InsertProvider(type=SubjectDetailSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(SubjectDetail record);

    @Select({
        "select",
        "id, subject_id, title, duration, vedio_url, play_count, is_delete, create_time, ",
        "modify_time",
        "from subject_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="duration", property="duration", jdbcType=JdbcType.BIGINT),
        @Result(column="vedio_url", property="vedioUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="play_count", property="playCount", jdbcType=JdbcType.BIGINT),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    SubjectDetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=SubjectDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SubjectDetail record);

    @Update({
        "update subject_detail",
        "set subject_id = #{subjectId,jdbcType=BIGINT},",
          "title = #{title,jdbcType=VARCHAR},",
          "duration = #{duration,jdbcType=BIGINT},",
          "vedio_url = #{vedioUrl,jdbcType=VARCHAR},",
          "play_count = #{playCount,jdbcType=BIGINT},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SubjectDetail record);
}