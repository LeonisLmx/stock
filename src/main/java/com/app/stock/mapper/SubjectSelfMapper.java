package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.SubjectMapper;
import com.app.stock.model.SubjectDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/15
 */
public interface SubjectSelfMapper extends SubjectMapper {

    @Select({
            "<script>",
            "select a.id,a.subject_type_id as type_id,a.teacher_id,c.name as type,a.content,a.title,a.small_img,a.detail,a.common_price,a.vip_price,DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time,",
            "b.name as teacher_name,b.description,b.avatar from subject a left join teacher b on a.teacher_id = b.id",
            "left join subject_type c on a.subject_type_id = c.id",
            "where a.is_delete = 0 and b.is_delete = 0 and c.is_delete = 0",
            "<if test=\"type != null and type != ''\">",
            "and c.id = #{type}",
            "</if>",
            "<if test=\"isFree != null and isFree == 0\">and a.common_price = 0</if>",
            "<if test=\"isFree != null and isFree == 1\">and a.common_price != 0</if>",
            "<if test=\"orderType != null and orderType != ''\">",
                "order by a.${orderType}",
                "<if test=\"orderType != null and orderType != ''\">",
                "${order}",
                "</if>",
            "</if>",
            "</script>"
    })
    List<Map<String,Object>> list(@Param("type")Integer type,@Param("isFree")Integer isFree,
            @Param("orderType")String orderType,@Param("order")String order);

    @Select({
            "select a.id,c.name as type,a.content,a.title,a.small_img,a.detail,a.common_price,a.vip_price,DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time,",
            "b.name as teacher_name,b.description,b.avatar from subject a left join teacher b on a.teacher_id = b.id",
            "left join subject_type c on a.subject_type_id = c.id",
            "where a.is_delete = 0 and b.is_delete = 0 and c.is_delete = 0 and a.id = #{id}",
    })
    Map<String,Object> detail(@Param("id")Long id);

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update subject set teacher is null where id = #{entity}",
            "</foreach>",
            "</script>"
    })
    int batchUpdateSubjectTeacher(@Param("list")List<Long> list);

    @Select({
            "select * from subject_detail where subject_id = #{subjectId} and is_delete = 0"
    })
    List<SubjectDetail> selectAllInfos(@Param("subjectId")Long subjectId);
}
