package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.PetStockDetailMapper;
import com.app.stock.model.PetStockDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lmx
 * Date 2018/12/29
 */
public interface PetStockDetailSelfMapper extends PetStockDetailMapper {

    @Select({
            "select * from pet_stock_detail where pet_id = #{petId} and is_delete = 0 order by create_time"
    })
    List<PetStockDetail> selectListByPetId(@Param("petId")Long petId);

    @Update({
            "update pet_stock_detail set is_delete = 1 where pet_id = #{petId}"
    })
    int deleteAllStockByPetId(@Param("petId")Long petId);

    @Select({
            "select count(*) from pet_stock_detail where pet_id = #{petId} and DATE_FORMAT(create_time,'%Y-%m-%d') = #{date}"
    })
    int selectCountByDateAndPetId(@Param("petId")Long petId,@Param("date")String date);

    @Select({
            "select a.* from pet_stock_detail a left join pet b on a.pet_id = b.id",
            "where a.stock_id = ${stockId} and b.user_id = #{userId} and a.is_delete = 0 and a.s_price is null"
    })
    PetStockDetail selectIsHaveStock(@Param("stockId")Long stockId,@Param("userId")Long userId);

    @Select({
            "select * from pet_stock_detail where s_price is null and is_delete = 0"
    })
    List<PetStockDetail> selectAllCalcStocks();

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update pet_stock_detail set s_price = #{entity.sPrice},s_time = #{entity.sTime} where id = #{entity.id}",
            "</foreach>",
            "</script>"
    })
    int batchUpdate(@Param("list")List<PetStockDetail> list);
}
