package com.app.stock.controller;

import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.app.stock.annoation.PostVerify;
import com.app.stock.common.Response;
import com.app.stock.model.User;
import com.app.stock.model.request.*;
import com.app.stock.service.AdvertiseService;
import com.app.stock.service.UserService;
import com.app.stock.serviceImpl.CommonserviceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/24
 * 用户相关操作controller
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertiseService advertiseService;

    @Autowired
    private CommonserviceImpl commonservice;

    // 获取手机验证码
    @RequestMapping(value = "/get_message_code",method = {RequestMethod.GET,RequestMethod.POST})
    @PostVerify
    public Response getMessageCode(@Valid @RequestBody PhoneCodeRequest request, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(userService.getMessageCode(request.getPhone(),request.getType()));
    }

    // 用户注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @PostVerify
    public Response register(@Valid @RequestBody UserRegisterRequest map, BindingResult bindingResult) throws NoSuchAlgorithmException{
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        Object result = userService.userRegister(map);
        if(result instanceof String){
            return Response.ok(result.toString());
        }else {
            return Response.ok(result, "注册成功");
        }
    }


    // 用户手机号登录
    @RequestMapping(value = "/login/password",method = {RequestMethod.GET,RequestMethod.POST})
    @PostVerify
    public Response loginByPassword(@Valid @RequestBody UserLoginPhoneRequest requestMap,BindingResult bindingResult) throws NoSuchAlgorithmException{
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        Object result = userService.loginByPassword(requestMap.getPhone(),requestMap.getPassword());
        if(result instanceof String){
            return Response.ok(result.toString());
        }else {
            return Response.ok(result, "登录成功");
        }
    }

    // 用户token登录
    @RequestMapping(value = "/login/token",method = {RequestMethod.GET,RequestMethod.POST})
    @PostVerify
    public Response LoginByToken(@Valid @RequestBody UserLoginTokenRequest requestMap,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        Object result = userService.LoginByToken(requestMap.getPhone(),requestMap.getToken());
        if(result instanceof String){
            return Response.ok(result.toString());
        }else {
            return Response.ok(result, "登录成功");
        }
    }

    // 验证码登录
    @RequestMapping(value = "/login/message_code",method = {RequestMethod.GET,RequestMethod.POST})
    @PostVerify
    public Response loginMessageCode(@Valid @RequestBody UserLoginCodeRequest requestMap, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        Object result = userService.loginByCode(requestMap.getPhone(),requestMap.getCode());
        if(result instanceof String){
            return Response.ok(result.toString());
        }else {
            return Response.ok(result, "登录成功");
        }
    }

    // 上传头像
    @RequestMapping(value = "/upload_avatar",method = RequestMethod.POST)
    public Response uploadAvatar(@RequestBody Map<String, String> map) throws IOException {
        if(map.get("imgStr") == null){
            return Response.ok("图片不能为空");
        }
        return Response.ok(advertiseService.uploadImage(1,map.get("imgStr")),"上传成功");
    }

    // 修改用户信息
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PostVerify
    public Response editInfo(@RequestBody Map<String,String> map,HttpServletRequest request){
        userService.editUser(map,request);
        return Response.ok("修改信息成功");
    }

    // 验证码找回密码
    @RequestMapping(value = "/find_password",method = RequestMethod.POST)
    @PostVerify
    public Response findPassword(@Valid @RequestBody UserFindPasswordCodeRequest map,BindingResult bindingResult) throws NoSuchAlgorithmException {
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(userService.findPasswordByCode(map.getPhone(),map.getPassword(),map.getCode()));
    }

    // 旧密码修改新密码
    @RequestMapping(value = "/edit_password",method = RequestMethod.POST)
    @PostVerify
    public Response editPassword(@Valid @RequestBody UserFindPasswordOldRequest map,BindingResult bindingResult,HttpServletRequest request) throws NoSuchAlgorithmException{
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        User user = commonservice.getCurrentInfo(request);
        return Response.ok(userService.editPasswordByOld(user,map.getOldPassword(),map.getNewPassword()));
    }

    // 获取用户信息
    @RequestMapping(value = "/get_info",method = {RequestMethod.GET,RequestMethod.POST})
    public Response getInfo(HttpServletRequest request){
        User user = commonservice.getCurrentInfo(request);
        Object result = userService.getUserInfo(user.getId());
        if(result instanceof String){
            return Response.ok(result.toString());
        }else {
            return Response.ok(result, "登录成功");
        }
    }
}
