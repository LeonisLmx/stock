package com.app.stock.controller;

import com.alipay.api.AlipayApiException;
import com.app.stock.common.Response;
import com.app.stock.model.request.AlipayRequest;
import com.app.stock.service.AlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by lmx
 * Date 2019/1/16
 */
@RestController
@RequestMapping("/api/alipay")
public class AlipayController {

    private static Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayService alipayService;


    @RequestMapping(value="/alipayOrder")
    public Response getAlipayOrder(@Valid @RequestBody AlipayRequest alipayRequest, BindingResult bindingResult,
                                   HttpServletResponse httpResponse, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return Response.ok(bindingResult.getFieldError().getDefaultMessage());
        }
        if(Double.valueOf(alipayRequest.getTotalAmount()).compareTo(0d) < 0){
            return Response.ok("价格不能小于0");
        }
        alipayService.getAlipayOrder(alipayRequest,request,httpResponse);
        return null;
    }


    @RequestMapping("/alipayNotify")
    public String notifyApp(HttpServletRequest request) throws AlipayApiException{
        return alipayService.notifyAlipay(request);
    }

}
