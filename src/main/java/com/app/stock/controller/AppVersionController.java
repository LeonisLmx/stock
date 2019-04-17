package com.app.stock.controller;

import com.app.stock.annoation.PostVerify;
import com.app.stock.common.Response;
import com.app.stock.model.AppVersion;
import com.app.stock.model.request.AppVersionAddRequest;
import com.app.stock.model.request.AppVersionSearchRequest;
import com.app.stock.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author mingxin Liu
 * @Date 2019-03-23 23:03
 * @Description //app version相关操作
 */
@RestController
@RequestMapping("/api/version")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    // 添加app版本号
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @PostVerify
    public Response addVersion(@Valid @RequestBody AppVersionAddRequest appVersion, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        String reason = appVersionService.addVersion(appVersion) == -1?"版本已存在":"操作成功";
        return Response.ok(reason);
    }

    // 修改app版本号
    @RequestMapping(value = "/edit",method = {RequestMethod.POST})
    public Response editVersion(@RequestBody AppVersion appVersion){
        if(appVersion.getId() == null){
            return Response.ok("id不能为空");
        }
        String reason = appVersionService.editVersion(appVersion) == -1?"版本已存在":"操作成功";
        return Response.ok(reason);
    }

    // 查询app版本号
    @RequestMapping(value = "/search",method = {RequestMethod.POST,RequestMethod.GET})
    public Response searchVersion(@RequestBody AppVersionSearchRequest appVersion){
        return Response.ok(appVersionService.searchList(appVersion),"操作成功");
    }
}
