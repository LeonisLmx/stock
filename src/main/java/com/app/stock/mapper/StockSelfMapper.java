package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.StockMapper;
import com.app.stock.model.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/7
 */
public interface StockSelfMapper extends StockMapper {

    @Select({
            "select id,stock_code from stock"
    })
    List<Map<String,Object>> selectAllStockCode();

    @Select({
            "<script>",
            "select * from stock where 1 = 1",
            "<if test=\"keywords != null and keywords != ''\">",
            "and (stock_code like '%${keywords}%' or stock_name like '%${keywords}%')",
            "order by stock_code",
            "</if>",
            "</script>"
    })
    List<Stock> selectAllByKeywords(@Param("keywords")String keywords);
}
