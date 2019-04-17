package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.model.WishTree;
import com.app.stock.model.request.WishListRequest;
import com.app.stock.service.WishTreeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/2/24
 * 许愿树相关controller
 */
@RequestMapping("/api/wish")
@RestController
public class WishTreeController {

    @Autowired
    private WishTreeInterface wishTreeInterface;

    // 获取所有愿望，带条件筛选
    @RequestMapping(value = "/allInfo",method = {RequestMethod.GET,RequestMethod.POST})
    public Response allInfo(@RequestBody WishListRequest wishListRequest, HttpServletRequest request){
        return Response.ok(wishTreeInterface.getAllInfo(request,wishListRequest),"操作成功");
    }

    // 获取单个愿望
    @RequestMapping(value = "/get",method = {RequestMethod.GET,RequestMethod.POST})
    public Response get(@RequestBody Map<String,Object> map){
        if(map.get("id") == null || map.get("id") == ""){
            return Response.ok("id不能为空");
        }
        return Response.ok(wishTreeInterface.getAloneWish(Long.valueOf(map.get("id") + "")),"操作成功");
    }

    // 新增或修改愿望
    @RequestMapping(value = "/operate",method = {RequestMethod.GET,RequestMethod.POST})
    public Response addWish(@Valid @RequestBody WishTree wishTree, BindingResult bindingResult,HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        int result = wishTreeInterface.operateWish(wishTree,request);
        if(result == -1){
            return Response.ok("愿望不存在");
        }else if(result == -2){
            return Response.ok("当月已经许愿过");
        }
        return Response.ok("操作成功");
    }

    // 删除愿望
    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public Response deleteWish(@RequestBody Map<String,Object> map){
        if(map.get("id") == null || map.get("id") == ""){
            return Response.ok("id不能为空");
        }
        int result = wishTreeInterface.deleteWish(Long.valueOf(map.get("id") + ""));
        if(result == -1){
            return Response.ok("愿望不存在");
        }
        return Response.ok("删除成功");
    }
}
