package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.WishTreeMapper;
import com.app.stock.model.WishTree;
import com.app.stock.model.request.WishListRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/2/24
 */
public interface WishTreeSelfMapper extends WishTreeMapper {

    @Select({
            "<script>",
            "select id,user_id,wish,wish_month,is_delete,date_format(create_time,'%Y-%m-%d') as createTime from wish_tree where 1 = 1",
            "<if test=\"entity.wishMonth != null and entity.wishMonth != ''\">",
            "and wish_month = #{entity.wishMonth}",
            "</if>",
            "<if test=\"entity.wish != null and entity.wish != ''\">",
            "and wish like %${entity.wish}%",
            "</if>",
            "<if test=\"entity.wishYear != null and entity.wishYear != ''\">",
            "and SUBSTR(wish_month,1,4) = #{entity.wishYear}",
            "</if>",
            "<if test=\"userId != null\">",
            "and user_id = #{userId}",
            "</if>",
            "order by wish_month",
            "</script>"
    })
    List<Map<String,Object>> selectAllInfoByCondition(@Param("entity") WishListRequest wishListRequest,@Param("userId")Long userId);

    @Select({
            "select count(*) from wish_tree where user_id = #{userId} and wish_month = #{wishMonth} and is_delete = 0"
    })
    int selectIsWishByMonth(@Param("userId")Long userId,@Param("wishMonth")String wishMonth);

    @Select({
            "select wish,wish_month,date_format(create_time,'%Y-%m-%d') as createTime from wish_tree where id = #{id}"
    })
    Map<String,Object> selectPrimarykeyById(@Param("id")Long id);
}
