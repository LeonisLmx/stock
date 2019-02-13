package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.AdvertiseMapper;
import com.app.stock.model.Advertise;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/26
 */
public interface AdvertiseSelfMapper extends AdvertiseMapper {

    @Select({
            "select id,title,image_url as imageUrl,type,to_url as toUrl,date_format(create_time,'%Y-%m-%d %H:%i:%s') as createTime,",
            "date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modifyTime from advertise where is_delete = 0 order by create_time"
    })
    List<Map<String,Object>> selectList();
}
