package com.app.stock.controller;

import com.app.stock.common.Response;
import com.app.stock.model.request.AlipayRequest;
import com.app.stock.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
@RequestMapping("/api/vip")
@RestController
public class VipController {

    @Autowired
    private AlipayService alipayService;

    // 支付vip
    @RequestMapping(value = "/pay_vip",method = {RequestMethod.GET,RequestMethod.POST})
    public Response payVip(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        if(map.get("month") == null || map.get("cost") == null || map.get("html") == null || map.get("cost_type") == null){
            return Response.ok("参数不能为空");
        }
        if("ZHIFUBAO".equals(map.get("cost_type"))) {
            AlipayRequest alipayRequest = new AlipayRequest();
            alipayRequest.setSuccessHtml(map.get("html").toString());
            alipayRequest.setSubject("VIP" + map.get("month") + "月");
            alipayRequest.setConditionId(Long.valueOf(map.get("month").toString()));
            alipayRequest.setTotalAmount(map.get("cost").toString());
            alipayRequest.setType(2);
            alipayService.getAlipayOrder(alipayRequest, request, response);
        }
        return null;
    }
}
