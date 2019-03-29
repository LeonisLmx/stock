package com.app.stock.controller;

import com.app.stock.annoation.PostVerify;
import com.app.stock.common.Response;
import com.app.stock.model.StockComment;
import com.app.stock.model.User;
import com.app.stock.service.StockCommentService;
import com.app.stock.serviceImpl.CommonserviceImpl;
import com.app.stock.serviceImpl.StockCommentServiceImpl;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/28
 */
@RestController
@RequestMapping("/api/comment")
public class StockCommentController {

    @Autowired
    private StockCommentService stockCommentService;

    // 发布吐槽
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @PostVerify
    public Response publishComment(@RequestBody StockComment stockComment, HttpServletRequest request){
        int result = stockCommentService.publishComment(stockComment,request);
        if(result == -1){
            return Response.ok("用户已被屏蔽");
        }
        return Response.ok("操作成功");
    }

    // 获取吐槽接口
    @RequestMapping(value = "/get_comment",method = {RequestMethod.GET,RequestMethod.POST})
    public Response getComment(@RequestBody Map<String,Object> requestMap,HttpServletRequest request){
        if(requestMap.get("stockCode") == null || requestMap.get("stockMarket") == null){
            return Response.ok("股票相关信息不能为空");
        }
        return Response.ok(stockCommentService.getComment(requestMap.get("type") == null?0:Integer.valueOf(requestMap.get("type") + ""),
                requestMap.get("stockCode").toString(), requestMap.get("stockMarket").toString(),
                requestMap.get("date") == null?null:requestMap.get("date").toString(),
                requestMap.get("page") == null?1:Integer.valueOf(requestMap.get("page") + ""),
                requestMap.get("page_size") == null?20:Integer.valueOf(requestMap.get("page_size") + ""),request),"操作成功");
    }
}
