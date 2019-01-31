package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.SubjectTypeMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
public interface SubjectTypeSelfMapper extends SubjectTypeMapper {

    @Select({
            "select id,name from subject_type where is_delete = 0 order by id"
    })
    List<Map<String,Object>> typeList();
}
