package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.AppVersionMapper;
import com.app.stock.model.AppVersion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author mingxin Liu
 * @Date 2019-03-23 23:13
 * @Description //TODO
 */
public interface AppversionSelfMapper extends AppVersionMapper {

    @Select({
            "select count(0) from app_version where version = #{version} and is_delete = 0"
    })
    int selectIsHave(@Param("version")String version);

    @Select({
            "<script>",
            "select id,version,description,type,link_url as linkUrl,",
            "date_format(create_time,'%Y-%m-%d %H:%i:%s') as createTime,",
            "date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modifyTime from app_version where 1 = 1",
            "<if test=\"entity.id != null\">",
            "and id = #{entity.id}",
            "</if>",
            "<if test=\"entity.version != null and entity.version != ''\">",
            "and version like '%${entity.version}%'",
            "</if>",
            "<if test=\"entity.type != null\">",
            "and type = #{entity.type}",
            "</if>",
            "<if test=\"entity.description != null and entity.description != ''\">",
            "and description like '%${entity.description}%'",
            "</if>",
            "<if test=\"entity.linkUrl != null and entity.linkUrl != ''\">",
            "and link_url = #{linkUrl}",
            "</if>",
            "order by create_time desc",
            "</script>"
    })
    List<Map<String,Object>> selectListByCondition(@Param("entity")AppVersion entity);
}
