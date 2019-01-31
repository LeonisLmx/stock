package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.SubjectMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/15
 */
public interface SubjectSelfMapper extends SubjectMapper {

    @Select({
            "<script>",
            "select a.id,c.name as type,a.content,a.title,a.small_img,a.detail,a.common_price,a.vip_price,DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time,",
            "b.name as teacher_name,b.description,b.avatar from subject a left join teacher b on a.teacher_id = b.id",
            "left join subject_type c on a.subject_type_id = c.id",
            "where a.is_delete = 0 and b.is_delete = 0 and c.is_delete = 0",
            "<if test=\"orderType != null and orderType != ''\">",
            "order by a.${orderType}",
            "<if test=\"orderType != null and orderType != ''\">",
            "${order}",
            "</if>",
            "</if>",
            "</script>"
    })
    List<Map<String,Object>> list(@Param("orderType")String orderType,@Param("order")String order);

    @Select({
            "select a.id,c.name as type,a.content,a.title,a.small_img,a.detail,a.common_price,a.vip_price,DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time,",
            "b.name as teacher_name,b.description,b.avatar from subject a left join teacher b on a.teacher_id = b.id",
            "left join subject_type c on a.subject_type_id = c.id",
            "where a.is_delete = 0 and b.is_delete = 0 and c.is_delete = 0 and a.id = #{id}",
    })
    Map<String,Object> detail(@Param("id")Long id);
}