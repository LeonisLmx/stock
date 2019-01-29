package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.CostDetailMapper;
import com.app.stock.model.CostDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by lmx
 * Date 2019/1/17
 */
public interface CostDetailSelfMapper extends CostDetailMapper {

    @Select({
            "select * from cost_detail where order_number = #{orderNumber} and is_delete = 0"
    })
    CostDetail selectPrimaryKeyByOrderNumber(@Param("orderNumber")String orderNumber);
}
