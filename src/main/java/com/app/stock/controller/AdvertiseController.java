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
@Controller
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    // 上传广告
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ResponseBody
    public Response publishAdversise(@RequestBody AdvertiseRequest advertise) throws IOException {
        if(StringUtils.isBlank(advertise.getImgStr())){
            return Response.ok("图片不能为空");
        }
        advertiseService.uploadAdvertise(advertise.getImgStr(),advertise);
        return Response.ok("上传成功");
    }

    // 广告列表
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public String advertiseList(Model model) {
        List<Map<String,Object>> list = advertiseService.list();
        //return Response.ok(advertiseService.list(),"操作成功");
        model.addAttribute("list",list);
        return "Advertise";
    }

    @RequestMapping(value = "/info",method = {RequestMethod.GET})
    public String advertiseInfo(Model model,Long id){
        System.out.println(id);
        if(id != null){
            model.addAttribute("entity",null);
        }
        return "AdvertiseEdit";
    }
}
