package com.app.stock.mapper.auto_generate;

import com.app.stock.model.ScoreDetail;
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

public interface ScoreDetailMapper {
    @Delete({
        "delete from score_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into score_detail (user_id, score, ",
        "from_type, is_delete, ",
        "create_time, modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{score,jdbcType=INTEGER}, ",
        "#{fromType,jdbcType=VARCHAR}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(ScoreDetail record);

    @InsertProvider(type=ScoreDetailSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(ScoreDetail record);

    @Select({
        "select",
        "id, user_id, score, from_type, is_delete, create_time, modify_time",
        "from score_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
        @Result(column="from_type", property="fromType", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    ScoreDetail selectByPrimaryKey(Long id);

    @UpdateProvider(type=ScoreDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScoreDetail record);

    @Update({
        "update score_detail",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "score = #{score,jdbcType=INTEGER},",
          "from_type = #{fromType,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ScoreDetail record);
}