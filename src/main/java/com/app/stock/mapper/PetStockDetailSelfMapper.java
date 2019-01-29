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
}
