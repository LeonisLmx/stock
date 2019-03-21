package com.app.stock.controller;

import com.app.stock.annoation.PostVerify;
import com.app.stock.common.Response;
import com.app.stock.model.AccountBook;
import com.app.stock.model.request.AccountListRequest;
import com.app.stock.model.request.AccountSummarizeRequest;
import com.app.stock.model.request.AccountTypeRequest;
import com.app.stock.service.AccountService;
import com.app.stock.service.StoreTypeService;
import org.apache.commons.lang3.StringUtils;
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
 * Date 2019/1/4
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private StoreTypeService storeTypeService;

    // 获取所有的账单类型
    @RequestMapping(value = "/get_type",method = {RequestMethod.GET,RequestMethod.POST})
    public Response getTypeInfo(@RequestBody AccountTypeRequest requestMap){
        return Response.ok(storeTypeService.getTypeInfo(requestMap.getIncome(),requestMap.getId()),"操作成功");
    }

    // 添加账单
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PostVerify
    public Response addNewAccount(@Valid @RequestBody AccountBook accountBook, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok( accountService.addNewAccount(accountBook,request));
    }

    // 删除账单
    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public Response deleteAccount(@RequestBody Map<String,Object> requestMap){
        if(requestMap.get("id") == null){
            return Response.ok("主键不能为空");
        }
        return Response.ok(accountService.deleteAccount(Long.valueOf(requestMap.get("id") + "")));
    }

    // 修改账单信息
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Response editAccount(@RequestBody AccountBook accountBook, BindingResult bindingResult){
        if(accountBook.getId() == null){
            return Response.ok("主键不能为空");
        }
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(accountService.editAccount(accountBook));
    }

    // 获取账单列表
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public Response accountList(@RequestBody AccountListRequest accountListRequest){
        return Response.ok(accountService.list(accountListRequest),"操作成功");
    }

    // 根据月份总结
    @RequestMapping(value = "/month",method = {RequestMethod.GET,RequestMethod.POST})
    public Response month(@RequestBody AccountSummarizeRequest accountSummarizeRequest,HttpServletRequest request){
        if(StringUtils.isBlank(accountSummarizeRequest.getMonth())){
            return Response.ok("月份不能为空");
        }
        return Response.ok(accountService.monthList(accountSummarizeRequest,request),"操作成功");
    }

    // 根据年份总结
    @RequestMapping(value = "/year",method = {RequestMethod.GET,RequestMethod.POST})
    public Response year(@RequestBody AccountSummarizeRequest accountSummarizeRequest,HttpServletRequest request){
        if(StringUtils.isBlank(accountSummarizeRequest.getYear())){
            return Response.ok("年份不能为空");
        }
        return Response.ok(accountService.yearList(accountSummarizeRequest,request),"操作成功");
    }
}
