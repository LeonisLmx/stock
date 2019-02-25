package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.SubjectDetailMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
public interface SubjectDetailSelfMapper extends SubjectDetailMapper {

    @Select({
            "select id,vedio_url,play_count from subject_detail where is_delete = 0 and subject_id = #{subjectId}"
    })
    List<Map<String,Object>> vedioList(@Param("subjectId")Long subjectId);

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update subject_detail set is_delete = 1 where id = #{entity}",
            "</foreach>",
            "</script>"
    })
    int batchUpdate(@Param("list")List<Long> list);
}
