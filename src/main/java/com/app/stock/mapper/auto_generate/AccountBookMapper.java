package com.app.stock.mapper.auto_generate;

import com.app.stock.model.AccountBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AccountBookMapper {
    @Delete({
        "delete from account_book",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into account_book (user_id, store_type_id, ",
        "serial_number, pay_type, ",
        "cost, content, is_delete, ",
        "create_time, modify_time)",
        "values (#{userId,jdbcType=BIGINT}, #{storeTypeId,jdbcType=BIGINT}, ",
        "#{serialNumber,jdbcType=VARCHAR}, #{payType,jdbcType=VARCHAR}, ",
        "#{cost,jdbcType=DECIMAL}, #{content,jdbcType=VARCHAR}, #{isDelete,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(AccountBook record);

    @InsertProvider(type=AccountBookSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(AccountBook record);

    @Select({
        "select",
        "id, user_id, store_type_id, serial_number, pay_type, cost, content, is_delete, ",
        "create_time, modify_time",
        "from account_book",
        "where id = #{id,jdbcType=BIGINT} and is_delete = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="store_type_id", property="storeTypeId", jdbcType=JdbcType.BIGINT),
        @Result(column="serial_number", property="serialNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="pay_type", property="payType", jdbcType=JdbcType.VARCHAR),
        @Result(column="cost", property="cost", jdbcType=JdbcType.DECIMAL),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    AccountBook selectByPrimaryKey(Long id);

    @UpdateProvider(type=AccountBookSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AccountBook record);

    @Update({
        "update account_book",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "store_type_id = #{storeTypeId,jdbcType=BIGINT},",
          "serial_number = #{serialNumber,jdbcType=VARCHAR},",
          "pay_type = #{payType,jdbcType=VARCHAR},",
          "cost = #{cost,jdbcType=DECIMAL},",
          "content = #{content,jdbcType=VARCHAR},",
          "is_delete = #{isDelete,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(AccountBook record);
}