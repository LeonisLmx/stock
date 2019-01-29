package com.app.stock.mapper;

import com.app.stock.mapper.auto_generate.UserMapper;
import com.app.stock.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/24
 */
public interface UserSelfMapper extends UserMapper {

    @Select({
            "<script>",
            "select * from user where phone = #{phone}",
            "<if test=\"password != null\">",
            "and password = #{password}",
            "</if>",
            "and is_delete = 0",
            "</script>"
    })
    User queryUserDetail(@Param("phone")String phone, @Param("password")String password);

    @Select({
            "select * from user where id = #{id} and is_delete = 0"
    })
    User selectByPrimaryKey(@Param("id")Long id);

    @Select({
            "<script>",
            "select * from user where",
            "<if test=\"phone != null\">",
            "phone = #{phone} and",
            "</if>",
            "token = #{token} and is_delete = 0",
            "</script>"
    })
    User selectByPrimaryToken(@Param("phone")String phone,@Param("token")String token);

    @Update({
            "<script>",
            "<foreach collection='list' item='entity' open='' close='' separator=';'>",
            "update user set is_shield = #{isShield} where id = #{entity}",
            "</foreach>",
            "</script>"
    })
    int updateUserIsShield(@Param("isShield")Long isShield,@Param("list") List<Long> list);
}
