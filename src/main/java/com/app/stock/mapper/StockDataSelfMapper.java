package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.StockDataMapper;
import com.app.stock.model.StockData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lmx
 * Date 2019/1/7
 */
public interface StockDataSelfMapper extends StockDataMapper {

    @Insert({
            "<script>",
            "insert into stock_data (stock_id,open,close,high,low,volume,trading_day)",
            "values",
            "<foreach collection='list' item='entity' index='index' separator=','>",
            "(#{entity.stockId},#{entity.open},#{entity.close},#{entity.high},#{entity.low},#{entity.volume},#{entity.tradingDay})",
            "</foreach>",
            "</script>"
    })
    int batchInsert(@Param("list")List<StockData> list);

    @Select({
            "<script>",
            "select stock_id,open,close,high,low,trading_day from stock_data where trading_day in",
            "<foreach collection='list' item='entity' open='(' close=')' separator=','>",
                "#{entity}",
            "</foreach>",
            "order by trading_day",
            "</script>"
    })
    List<StockData> selectDatasByTradingDay(@Param("list")List<String> list);

    @Select({
            "<script>",
            "select stock_code,stock_name,market from stock where id in",
            "<foreach collection='list' item='entity' open='(' close=')' separator=','>",
            "#{entity}",
            "</foreach>",
            "</script>"
    })
    List<Map<String,Object>> selectStocksByList(@Param("list") Set<String> list);

    @Select({
            "select a.stock_code,a.stock_name,b.open,b.close,b.high,b.low,b.volume,b.trading_day from stock a left join",
            "stock_data b on a.id = b.stock_id where a.stock_code = #{stockCode} and a.market = #{market} order by b.trading_day"
    })
    List<Map<String,Object>> selectStockInfoByCode(@Param("stockCode")String stockCode,@Param("market")String market);

    @Select({
            "select stock_id,open,close,high,low,trading_day from stock_data where stock_id = #{stockId} order by trading_day desc"
    })
    List<StockData> selectStocksByStockId(@Param("stockId")Long stockId);

    @Select({
            "select close from stock_data where stock_id = #{stockId} and trading_day = #{date}"
    })
    BigDecimal selectClosePrice(@Param("stockId")Long stockId, @Param("date")String date);
}
