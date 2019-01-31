package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.model.request.PrimarykeyIdRequest;
import com.app.stock.model.request.SubjectListRequest;
import com.app.stock.service.SubjectService;
import com.app.stock.service.SubjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by lmx
 * Date 2019/1/15
 */
@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectTypeService subjectTypeService;

    // 获取所有的课程分类列表
    @RequestMapping(value = "/type_list",method = RequestMethod.POST)
    public Response typeList(){
        return Response.ok(subjectTypeService.typeList(),"操作成功");
    }

    // 获取list列表，包含排序规则
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public Response list(@RequestBody SubjectListRequest subjectListRequest){
        return Response.ok(subjectService.list(subjectListRequest),"操作成功");
    }

    // 查询课程是否付费
    @RequestMapping(value = "/isPay",method = {RequestMethod.POST})
    public Response isPay(@Valid @RequestBody PrimarykeyIdRequest primarykeyIdRequest, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(subjectService.isPay(primarykeyIdRequest,request),"操作成功");
    }

    // 获取课程详情
    @RequestMapping(value = "/detail",method = {RequestMethod.GET,RequestMethod.POST})
    public Response detail(@Valid @RequestBody PrimarykeyIdRequest primarykeyIdRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        return Response.ok(subjectService.detail(primarykeyIdRequest),"查询成功");
    }
}
