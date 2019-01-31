package com.app.stock.mapper.auto_generate;

import com.app.stock.model.Subject;
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

public interface SubjectMapper {
    @Delete({
        "delete from subject",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into subject (subject_type_id, teacher_id, ",
        "title, content, ",
        "small_img, detail, ",
        "common_price, vip_price, ",
        "is_delete, create_time, ",
        "modify_time)",
        "values (#{subjectTypeId,jdbcType=BIGINT}, #{teacherId,jdbcType=BIGINT}, ",
        "#{title,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{smallImg,jdbcType=VARCHAR}, #{detail,jdbcType=VARCHAR}, ",
        "#{commonPrice,jdbcType=DECIMAL}, #{vipPrice,jdbcType=DECIMAL}, ",
        "#{isDelete,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Subject record);

    @InsertProvider(type= com.app.stock.mapper.SubjectSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Subject record);

    @Select({
        "select",
        "id, subject_type_id, teacher_id, title, content, small_img, detail, common_price, ",
        "vip_price, is_delete, create_time, modify_time",
        "from subject",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="subject_type_id", property="subjectTypeId", jdbcType=JdbcType.BIGINT),
        @Result(column="teacher_id", property="teacherId", jdbcType=JdbcType.BIGINT),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="small_img", property="smallImg", jdbcType=JdbcType.VARCHAR),
        @Result(column="detail", property="detail", jdbcType=JdbcType.VARCHAR),
        @Result(column="common_price", property="commonPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="vip_price", property="vipPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Subject selectByPrimaryKey(Long id);

    @UpdateProvider(type= com.app.stock.mapper.SubjectSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Subject record);

    @Update({
        "update subject",
        "set subject_type_id = #{subjectTypeId,jdbcType=BIGINT},",
          "teacher_id = #{teacherId,jdbcType=BIGINT},",
          "title = #{title,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "small_img = #{smallImg,jdbcType=VARCHAR},",
          "detail = #{detail,jdbcType=VARCHAR},",
          "common_price = #{commonPrice,jdbcType=DECIMAL},",
          "vip_price = #{vipPrice,jdbcType=DECIMAL},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Subject record);
}