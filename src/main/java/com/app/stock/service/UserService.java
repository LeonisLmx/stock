package com.app.stock.service;

import com.app.stock.model.User;
import com.app.stock.model.request.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/26
 */
public interface UserService {

    public String getMessageCode(String phone,int type);

    public Object userRegister(UserRegisterRequest map) throws NoSuchAlgorithmException;

    public Object loginByPassword(String phone,String password) throws NoSuchAlgorithmException;

    public Object LoginByToken(String phone,String token);

    public int editUser(Map<String,String> map, HttpServletRequest request);

    public Object loginByCode(String phone,String code);

    public String findPasswordByCode(String phone,String password,String code) throws NoSuchAlgorithmException;

    public String editPasswordByOld(User user,String oldPassword,String newPassword) throws NoSuchAlgorithmException;

    public void editPassword(User user,String newPassword) throws NoSuchAlgorithmException;

    public Object getUserInfo(Long id);

    public Object getUserInfoByAdmin(Long id);
}
