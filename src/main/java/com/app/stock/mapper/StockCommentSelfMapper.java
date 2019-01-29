package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.StockCommentMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/28
 */
public interface StockCommentSelfMapper extends StockCommentMapper {

    @Select({
            "<script>",
            "select a.nickname,a.phone,a.headerImg,b.content,DATE_FORMAT(b.create_time,'%Y-%m-%d %H:%i:%s') as create_time",
            "from user a left join stock_comment b",
            "on a.id = b.user_id where a.is_delete = 0 and b.is_delete = 0",
            "<if test=\"userId != null\">",
            "and a.id = #{userId}",
            "</if>",
            "<if test=\"stockCode != null and stockCode != ''\">",
            "and b.stock_code = #{stockCode}",
            "</if>",
            "<if test=\"stockMarket != null and stockMarket != ''\">",
            "and b.stock_market = #{stockMarket}",
            "</if>",
            "<if test=\"date != null and date != ''\">",
            "and DATE_FORMAT(b.create_time,'%Y-%m-%d') &lt;= #{date}",
            "</if>",
            "order by b.create_time desc",
            "</script>"
    })
    List<Map<String,Object>> selectStockCommentByCondition(@Param("userId")Long userId,@Param("stockCode")String stockCode,
                                                           @Param("stockMarket")String stockMarket,@Param("date")String date);

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update stock_comment set is_delete = #{isDelete} and user_id = #{entity}",
            "</foreach>",
            "</script>"
    })
    int updateStockComment(@Param("isDelete")int isDelete,@Param("list") List<Long> list);
}
