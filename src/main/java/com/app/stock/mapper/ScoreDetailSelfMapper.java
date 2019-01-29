package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.ScoreDetailMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by lmx
 * Date 2018/12/28
 */
public interface ScoreDetailSelfMapper extends ScoreDetailMapper {

    @Select({
            "select count(*) from score_detail where DATE_FORMAT(create_time,'%Y-%m-%d') = #{date}",
            "and from_type = #{from} and user_id = #{userId} and is_delete = 0"
    })
    int selectScoreDetailIsHaveByDateAndFrom(@Param("date") String date,@Param("from") String from,
                                             @Param("userId")Long userId);

    @Select({
            "select count(*) from score_detail where DATE_FORMAT(create_time,'%Y-%m') = #{date}",
            "and from_type = #{from} and user_id = #{userId} and is_delete = 0"
    })
    int selectScoreDetailIsHaveByMonthAndFrom(@Param("date") String date,@Param("from") String from,
                                             @Param("userId")Long userId);
}
