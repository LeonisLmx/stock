package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.UserSubjectDetailMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by lmx
 * Date 2019/1/17
 */
public interface UserSubjectDetailSelfMapper extends UserSubjectDetailMapper {

    @Select({
            "select count(*) from user_subject_detail where user_id = #{userId} and subject_id = #{subjectId}"
    })
    int selectIsPayBySubjectId(@Param("userId") Long userId, @Param("subjectId") Long subjectId);
}
