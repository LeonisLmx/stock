package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.StockMapper;
import com.app.stock.model.Stock;
import org.apache.ibatis.annotations.Insert;
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
            "</if>",
            "order by stock_code limit 20",
            "</script>"
    })
    List<Stock> selectAllByKeywords(@Param("keywords")String keywords);

    @Select({
            "<script>",
            "select concat(market,stock_code) from stock where id in",
            "<foreach collection='list' item='entity' open='(' close=')' separator=','>",
            "#{entity}",
            "</foreach>",
            "</script>"
    })
    List<String> selectStockCodes(@Param("list")List<String> list);

    @Insert({
            "<script>",
            "insert into stock (stock_code,stock_name,market)",
            "values",
            "<foreach collection='list' item='entity' index='index' separator=','>",
            "(#{entity.stockCode},#{entity.stockName},#{entity.market})",
            "</foreach>",
            "</script>"
    })
    int batchInsertStocks(@Param("list") List<Stock> list);
}
