package com.app.stock.serviceImpl;

import com.app.stock.common.CommonUtil;
import com.app.stock.common.RedisUtil;
import com.app.stock.common.commonEnum.SMSEnum;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.mapper.UserVipDetailSelfMapper;
import com.app.stock.model.User;
import com.app.stock.model.UserVipDetail;
import com.app.stock.model.request.UserRegisterRequest;
import com.app.stock.service.PetService;
import com.app.stock.service.SMSSerivce;
import com.app.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lmx
 * Date 2018/12/24
 */
@Service
public class UserServiceImpl implements UserService {

    private final static String front_key = "xungu";
    private final static String back_key = "app";

    @Autowired
    private UserSelfMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommonserviceImpl commonservice;

    @Autowired
    private UserVipDetailSelfMapper userVipDetailSelfMapper;

    @Autowired
    private PetService petService;

    @Autowired
    private SMSSerivce smsSerivce;

    // 根据类别获取短信验证码
    public String getMessageCode(String phone,int type) throws Exception {
        return smsSerivce.sendSMS(SMSEnum.getName(type),phone) == 1?"发送成功":"发送失败，请稍后重试";
    }

    // 用户注册
    public Object userRegister(UserRegisterRequest map) throws NoSuchAlgorithmException{
        if(!map.getCode().equals(redisUtil.get(map.getPhone() + SMSEnum.REGISTER.getCode()))){
            return "验证码不正确或验证码已超时";
        }
        redisUtil.remove(map.getPhone() + SMSEnum.REGISTER.getCode());
        if(userMapper.queryUserDetail(map.getPhone(),null) != null){
            return "该用户已存在";
        }
        User user = new User();
        user.setPhone(map.getPhone());
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] str = md5.digest((front_key + map.getPassword() + back_key).getBytes());
        user.setPassword(CommonUtil.byteArrayToHexString(str));
        user.setNickname(map.getNickname());
        String token =  UUID.randomUUID().toString();
        user.setToken(token);
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("token",token);
        returnMap.put("nickname",user.getNickname());
        returnMap.put("phone",user.getPhone());
        return returnMap;
    }

    // 用户登录,通过手机密码登录
    public Object loginByPassword(String phone,String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] str = md5.digest((front_key + password + back_key).getBytes());
        password = CommonUtil.byteArrayToHexString(str);
        User user = userMapper.queryUserDetail(phone,password);
        if(user == null){
            return "用户名或密码错误";
        }
        List<UserVipDetail> list = userVipDetailSelfMapper.selectAllVipDetailsByUserId(user.getId());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("id",user.getId());
        returnMap.put("phone", user.getPhone());
        returnMap.put("nickname", user.getNickname());
        returnMap.put("address",user.getAddress());
        returnMap.put("vip",list.size() == 0?0:1);
        returnMap.put("score",user.getScore());
        returnMap.put("descriptions",user.getDescriptions());
        returnMap.put("headerImg",user.getHeaderimg());
        returnMap.put("token", user.getToken());
        returnMap.put("pet",petService.isHavePeet(user.getId()));
        return returnMap;
    }

    // 用户登录，通过token登录
    public Object LoginByToken(String phone,String token){
        User user = userMapper.selectByPrimaryToken(phone,token);
        return returnUserInfo(user);
    }

    @Override
    public int editUser(Map<String,String> map, HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        user.setHeaderimg(map.get("headImg"));
        user.setNickname(map.get("nickname"));
        user.setAddress(map.get("address"));
        user.setDescriptions(map.get("descriptions"));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    // 验证码登录
    @Override
    public Object loginByCode(String phone, String code) {
        if(!code.equals(redisUtil.get(phone + SMSEnum.CODE_LOGIN.getCode()))){
            return "验证码不正确或验证码已超时";
        }
        redisUtil.remove(phone + SMSEnum.CODE_LOGIN.getCode());
        User user = userMapper.queryUserDetail(phone,null);
        return returnUserInfo(user);
    }

    // 验证码找回密码
    @Override
    public String findPasswordByCode(String phone, String password, String code) throws NoSuchAlgorithmException {
        if(!code.equals(redisUtil.get(phone + SMSEnum.RESET_PASSWORD.getCode()))){
            return "验证码不正确或验证码已超时";
        }
        redisUtil.remove(phone + SMSEnum.RESET_PASSWORD.getCode());
        User user = userMapper.queryUserDetail(phone,null);
        if(user == null){
            return "用户不存在";
        }
        editPassword(user,password);
        return "密码修改成功";
    }

    @Override
    public String editPasswordByOld(User user, String oldPassword, String newPassword) throws NoSuchAlgorithmException {
        if(oldPassword.equals(newPassword)){
            return "两次密码相同";
        }
        if(user == null || !oldPassword.equals(user.getPassword())){
            return "旧密码不对";
        }
        editPassword(user,newPassword);
        return "密码修改成功";
    }

    // 修改密码
    @Override
    public void editPassword(User user, String newPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] str = md5.digest((front_key + newPassword + back_key).getBytes());
        user.setPassword(CommonUtil.byteArrayToHexString(str));
        userMapper.updateByPrimaryKey(user);
    }

    // 获取用户信息
    @Override
    public Object getUserInfo(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return "用户不存在";
        }
        List<UserVipDetail> list = userVipDetailSelfMapper.selectAllVipDetailsByUserId(user.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("nickname",user.getNickname());
        returnMap.put("description",user.getDescriptions());
        returnMap.put("url",user.getHeaderimg());
        returnMap.put("address",user.getAddress());
        returnMap.put("score",user.getScore());
        returnMap.put("isVip",0);
        returnMap.put("failure_time",null);
        returnMap.put("pet",petService.isHavePeet(user.getId()));
        if(list.size() > 0){
            returnMap.put("isVip",1);
            returnMap.put("failure_time",sdf.format(list.get(0).getFailureTime()));
        }
        return returnMap;
    }

    @Override
    public Object getUserInfoByAdmin(Long id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<UserVipDetail> list = userVipDetailSelfMapper.selectAllVipDetailsByUserId(id);
        User user = userMapper.selectByPrimaryKey(id);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("nickname",user.getNickname());
        returnMap.put("description",user.getDescriptions());
        returnMap.put("url",user.getHeaderimg());
        returnMap.put("phone",user.getPhone());
        returnMap.put("headerimg",user.getHeaderimg());
        returnMap.put("address",user.getAddress());
        returnMap.put("score",user.getScore());
        returnMap.put("isVip",0);
        returnMap.put("failure_time",null);
        returnMap.put("pet",petService.isHavePeet(user.getId()));
        if(list.size() > 0){
            returnMap.put("isVip",1);
            returnMap.put("failure_time",sdf.format(list.get(0).getFailureTime()));
        }
        returnMap.put("isDelete",user.getIsDelete());
        returnMap.put("isAdmin",user.getIsAdmin());
        returnMap.put("isShield",user.getIsShield());
        returnMap.put("createTime",sdf.format(user.getCreateTime()));
        returnMap.put("modifyTime",sdf.format(user.getModifyTime()));
        return returnMap;
    }

    protected Object returnUserInfo(User user){
        if(user != null) {
            List<UserVipDetail> list = userVipDetailSelfMapper.selectAllVipDetailsByUserId(user.getId());
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("id",user.getId());
            returnMap.put("phone", user.getPhone());
            returnMap.put("nickname", user.getNickname());
            returnMap.put("address",user.getAddress());
            returnMap.put("score",user.getScore());
            returnMap.put("vip",list.size() == 0?0:1);
            returnMap.put("descriptions",user.getDescriptions());
            returnMap.put("headerImg",user.getHeaderimg());
            returnMap.put("token", user.getToken());
            returnMap.put("pet",petService.isHavePeet(user.getId()));
            return returnMap;
        }else{
            return "用户不存在";
        }
    }
}
