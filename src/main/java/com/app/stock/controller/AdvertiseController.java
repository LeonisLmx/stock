package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.model.Advertise;
import com.app.stock.model.request.AdvertiseRequest;
import com.app.stock.service.AdvertiseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/24
 */
@RequestMapping("/api/advertise")
@RestController
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    // 上传广告
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public Response publishAdversise(@RequestBody AdvertiseRequest advertise) throws IOException {
        if(advertise.getId() == null && StringUtils.isBlank(advertise.getImgStr())){
            return Response.ok("图片不能为空");
            // 否则就是修改
        }
        advertiseService.uploadAdvertise(advertise.getImgStr(),advertise);
        return Response.ok("上传成功");
    }

    // 广告列表
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public Response advertiseList() {
        return Response.ok(advertiseService.list(),"操作成功");
    }

    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    public Response deleteAdvertise(@RequestBody Advertise advertise){
        if(advertise.getId() == null){
            return Response.ok("id不能为空");
        }
        return Response.ok(advertiseService.deleteAdvertise(advertise.getId()),"删除成功");
    }
}
