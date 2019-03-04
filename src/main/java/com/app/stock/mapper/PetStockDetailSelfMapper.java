package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.PetStockDetailMapper;
import com.app.stock.model.PetStockDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/29
 */
public interface PetStockDetailSelfMapper extends PetStockDetailMapper {

    @Select({
            "<script>",
            "select a.stock_name,b.stock_code,b.market,a.b_price,date_format(a.b_time,'%Y-%m-%d %H:%i:%s') as b_time",
            "<if test=\"isDelete == 1\">",
            ",a.s_price,date_format(a.s_time,'%Y-%m-%d %H:%i:%s') as s_time,a.increase",
            "</if>",
            " from pet_stock_detail a left join stock b on a.stock_id = b.id",
            "where a.pet_id = #{petId} and a.is_delete = #{isDelete} order by a.create_time",
            "</script>"
    })
    List<Map<String,Object>> selectListByPetId(@Param("petId")Long petId, @Param("isDelete") Integer isDelete);

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
