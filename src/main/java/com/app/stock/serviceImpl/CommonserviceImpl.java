package com.app.stock.serviceImpl;

import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lmx
 * Date 2018/12/28
 */
@Service
public class CommonserviceImpl {

    @Autowired
    private UserSelfMapper userMapper;

    public User getCurrentInfo(HttpServletRequest request){
        return userMapper.selectByPrimaryToken(null,request.getHeader("token"));
    }
}
