package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.UserVipDetailMapper;
import com.app.stock.model.UserVipDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/18
 */
public interface UserVipDetailSelfMapper extends UserVipDetailMapper {

    @Select({
            "select * from user_vip_detail where user_id = #{userId} and is_delete = 0",
            "order by failure_time desc"
    })
    List<UserVipDetail> selectAllVipDetailsByUserId(@Param("userId")Long userId);

}
