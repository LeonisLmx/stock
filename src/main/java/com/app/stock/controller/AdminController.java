package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.model.Subject;
import com.app.stock.model.Teacher;
import com.app.stock.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectDetailService subjectDetailService;

    @Autowired
    private TeacherService teacherService;

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

    // 新增课程
    @RequestMapping(value = "/add_subject",method = RequestMethod.POST)
    public Response newSubject(@RequestBody @Valid Subject subject, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        subjectService.addSubject(subject);
        return Response.ok("新增成功");
    }

    // 修改课程主体信息
    @RequestMapping(value = "/edit_subject",method = RequestMethod.POST)
    public Response editSubject(@RequestBody Subject subject){
        if(subject.getId() == null){
            return Response.ok("修改主键不能为空");
        }
        subjectService.editSubject(subject);
        return Response.ok("修改成功");
    }

    // 删除课程下的视频信息
    @RequestMapping(value = "/edit_vedio",method = RequestMethod.POST)
    public Response editVedio(@RequestBody List<Long> list){
        subjectDetailService.deleteVideo(list);
        return Response.ok("操作成功");
    }

    // 上传视频
    @RequestMapping(value = "/upload_video",method = RequestMethod.POST)
    public Response uploadVideo(@RequestBody Map<String,String> map) throws IOException {
        if(StringUtils.isBlank(map.get("url")) || StringUtils.isBlank(map.get("subject_id"))){
            return Response.ok("必要参数不能为空");
        }
        return Response.ok(subjectDetailService.uploadVideo(map),"上传成功");
    }

    // 获取现有老师的列表
    @RequestMapping(value = "/teacher_list",method = RequestMethod.POST)
    public Response teacherList(){
        return Response.ok(teacherService.teacherList(),"操作成功");
    }

    // 新增老师信息
    @RequestMapping(value = "/new_teacher",method = RequestMethod.POST)
    public Response newTeacher(@RequestBody @Valid Teacher teacher,BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        teacherService.newTeacher(teacher);
        return Response.ok("操作成功");
    }

    // 修改老师主体信息
    @RequestMapping(value = "/edit_teacher",method = RequestMethod.POST)
    public Response editTeacher(@RequestBody Teacher teacher){
        if(teacher.getId() == null){
            return Response.ok("主键ID不能为空");
        }
        teacherService.editTeacher(teacher);
        return Response.ok("修改成功");
    }

    // 删除老师信息
    @RequestMapping(value = "/delete_teacher",method = RequestMethod.POST)
    public Response deleteTeacher(@RequestBody List<Long> ids){
        teacherService.deleteTeacher(ids);
        return Response.ok("删除老师信息成功");
    }

}
