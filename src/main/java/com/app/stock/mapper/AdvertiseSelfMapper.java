package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.AdvertiseMapper;
import com.app.stock.model.Advertise;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lmx
 * Date 2018/12/26
 */
public interface AdvertiseSelfMapper extends AdvertiseMapper {

    @Select({
            "select * from advertise where is_delete = 0 order by create_time"
    })
    List<Advertise> selectList();
}
