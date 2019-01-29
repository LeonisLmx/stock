package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.service.AdminService;
import com.app.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/23
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    // 获取用户信息
    @RequestMapping(value = "/getUser",method = {RequestMethod.GET,RequestMethod.POST})
    public Response getUser(@RequestBody Map<String,String> map){
        if(map.get("id") == null){
            return Response.ok(null,"id不能为空",1);
        }
        return Response.ok(userService.getUserInfoByAdmin(Long.valueOf(map.get("id"))),"操作成功");
    }

    // 屏蔽用户操作
    @RequestMapping(value = "/shield",method = {RequestMethod.GET,RequestMethod.POST})
    public Response shieldUser(@RequestBody Map<String,Object> map){
        if(map.get("ids") == null || !(map.get("ids") instanceof List) ||
        ((List)map.get("ids")).size() == 0 || map.get("isShield") == null){
            return Response.ok(null,"参数不能为空",1);
        }
        adminService.shieldUser((List)map.get("ids"),Long.valueOf(map.get("isShield") + ""));
        return Response.ok("操作成功");
    }

}
