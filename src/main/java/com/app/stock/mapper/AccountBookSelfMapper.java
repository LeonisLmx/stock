package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.AccountBookMapper;
import com.app.stock.model.request.AccountListRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/4
 */
public interface AccountBookSelfMapper extends AccountBookMapper {

    @Select({
            "<script>",
            "select a.id,b.name,(case when b.income = '1' then '支出' when b.income = '2' then '收入' else '其他' end) as income,a.serial_number,a.pay_type,a.cost,a.content,DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time",
            "from account_book a left join store_type b on a.store_type_id = b.id",
            "where a.is_delete = 0 and b.is_delete = 0",
            "<if test=\"entity.storeTypeId != null and entity.storeTypeId.size() > 0\">",
                "and a.store_type_id in",
                "<foreach collection='entity.storeTypeId' item='entity' open='(' close=')' separator=','>",
                "#{entity}",
                "</foreach>",
            "</if>",
            "<if test=\"entity.payType != null and entity.payType != ''\">",
            "and a.pay_type = #{entity.payType}",
            "</if>",
            "<if test=\"entity.cost != null\">",
            "and a.cost &lt;= #{entity.cost}",
            "</if>",
            "<if test=\"entity.content != null and entity.content != ''\">",
            "and a.content like '%${entity.content}%'",
            "</if>",
            "<if test=\"entity.startTime != null and entity.startTime != ''\">",
            "and a.create_time &gt;= #{entity.startTime}",
            "</if>",
            "<if test=\"entity.endTime != null and entity.endTime != ''\">",
            "and a.create_time &lt;= #{entity.endTime}",
            "</if>",
            "</script>"
    })
    List<Map<String,Object>> selectAllAccountByCondition(@Param("entity") AccountListRequest entity);

    @Select({
            "<script>",
            "select sum(a.cost) as cost,a.store_type_id,b.name from account_book a left join",
            "store_type b on a.store_type_id = b.id where DATE_FORMAT(a.create_time,'%Y-%m') = #{month}",
            "and a.user_id = #{userId} and a.is_delete = 0 and b.is_delete = 0",
            "<if test=\"list != null and list.size() > 0\">",
                "and a.store_type_id in",
                "<foreach collection='list' item='entity' open='(' close=')' separator=','>",
                "#{entity}",
                "</foreach>",
            "</if>",
            "group by store_type_id",
            "</script>"
    })
    List<Map<String,Object>> selectAllListByMonth(@Param("userId")Long userId,@Param("month")String month,@Param("list")List<Long> list);

    @Select({
            "<script>",
            "select sum(a.cost) as cost,a.store_type_id,b.name from account_book a left join",
            "store_type b on a.store_type_id = b.id where DATE_FORMAT(a.create_time,'%Y') = #{year}",
            "and a.user_id = #{userId} and a.is_delete = 0 and b.is_delete = 0",
            "<if test=\"list != null and list.size() > 0\">",
            "and a.store_type_id in",
            "<foreach collection='list' item='entity' open='(' close=')' separator=','>",
            "#{entity}",
            "</foreach>",
            "</if>",
            "group by store_type_id",
            "</script>"
    })
    List<Map<String,Object>> selectAllListByYear(@Param("userId")Long userId,@Param("year")String year,@Param("list")List<Long> list);
}
