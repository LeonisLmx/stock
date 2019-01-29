package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.PetMapper;
import com.app.stock.model.Pet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by lmx
 * Date 2018/12/29
 */
public interface PetSelfMapper extends PetMapper {

    @Select({
            "select * from pet where user_id = #{userId} and is_delete = 0"
    })
    Pet selectPrimarykeyByUserId(@Param("userId")Long userId);

}
