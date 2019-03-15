package com.app.stock.controller;

import com.app.stock.annoation.PostVerify;
import com.app.stock.common.Response;
import com.app.stock.model.request.FeedPetRequest;
import com.app.stock.service.PetService;
import com.app.stock.serviceImpl.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/29
 */
@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetService petService;

    // 新建一个宠物
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @PostVerify
    public Response createPet(@RequestBody Map<String,Object> map,HttpServletRequest request){
        if(map.get("name") == null){
            return Response.ok("宠物名称不能为空");
        }
        boolean flag = petService.createPet(map.get("name").toString(),request);
        return Response.ok(flag?"新增成功":"新增失败");
    }

    // 喂宠物（买入股票）
    @RequestMapping(value = "/feed",method = RequestMethod.POST)
    @PostVerify
    public Response feedPet(@Valid @RequestBody FeedPetRequest map, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(petService.feedPet(map,request));
    }

    // 卖出股票
    @RequestMapping(value = "/sale",method = RequestMethod.POST)
    @PostVerify
    public Response saleStock(@Valid @RequestBody FeedPetRequest map, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(petService.saleStock(map,request));
    }

    // 查询宠物信息
    @RequestMapping(value = "/info",method = {RequestMethod.GET,RequestMethod.POST})
    public Response petInfo(HttpServletRequest request){
        Map<String,Object> map = petService.petInfo(request);
        if(map == null){
            return Response.ok("该用户没有宠物");
        }
        return Response.ok(map,"操作成功");
    }

    // 重置宠物
    @RequestMapping(value = "/reset",method = {RequestMethod.GET,RequestMethod.POST})
    public Response resetPet(HttpServletRequest request){
        return Response.ok(petService.resetPet(request));
    }
}
