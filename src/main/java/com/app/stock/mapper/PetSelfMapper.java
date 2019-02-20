package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.PetMapper;
import com.app.stock.model.Pet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lmx
 * Date 2018/12/29
 */
public interface PetSelfMapper extends PetMapper {

    @Select({
            "select * from pet where user_id = #{userId} and is_delete = 0"
    })
    Pet selectPrimarykeyByUserId(@Param("userId")Long userId);

    @Update({
            "update pet set change_score = 0,percent = null where is_delete = 0"
    })
    int updatePetScheduled();

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update pet set percent = #{entity.percent},level = #{entity.level} where id = #{entity.id}",
            "</foreach>",
            "</script>"
    })
    int batchUpdate(@Param("list")List<Pet> list);

}
