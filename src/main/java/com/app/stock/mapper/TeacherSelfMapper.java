package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.TeacherMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
public interface TeacherSelfMapper extends TeacherMapper {

    @Select({
            "select id,name,description,avatar from teacher where is_delete = 0"
    })
    List<Map<String,Object>> teacherList();

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update teacher set is_delete = 1 where id = #{entity}",
            "</foreach>",
            "</script>"
    })
    int batchDelete(@Param("list")List<Long> list);

}
