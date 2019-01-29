package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.StoreTypeMapper;
import com.app.stock.model.StoreType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/4
 */
public interface StoreTypeSelfMapper extends StoreTypeMapper {

    // 查出所有一级菜单
    @Select({
            "<script>",
            "select * from store_type where is_delete = 0",
            "<if test=\"income != null\">",
            "and income = #{income}",
            "</if>",
            "order by income asc,id asc",
            "</script>"
    })
    List<StoreType> selectAll(@Param("income")Integer income);

    @Select({
            "select id from store_type where parent_id = #{parentId} and is_delete = 0"
    })
    List<Long> selectAllIdsByParent(@Param("parentId")Long parentId);

}
