package com.app.stock.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.app.stock.common.commonEnum.OrderTypeEnum;
import com.app.stock.common.commonEnum.ScoreEnum;
import com.app.stock.config.desgin.UserDetailFactory;
import com.app.stock.mapper.CostDetailSelfMapper;
import com.app.stock.model.CostDetail;
import com.app.stock.model.User;
import com.app.stock.model.request.AlipayRequest;
import com.app.stock.service.AlipayService;
import com.app.stock.service.CostDetailsService;
import com.app.stock.service.ScoreDetailService;
import com.app.stock.service.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.app.stock.config.Alipay.AlipayConfig.*;

/**
 * Created by lmx
 * Date 2019/1/17
 */
@Service
public class AlipayServiceImpl extends CostDetailsService implements AlipayService {

    private static Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Value("${spring.server.url}")
    private String url;

    // 初始化 alipay 客户端
    private static AlipayClient alipayClient = new DefaultAlipayClient(APP_URL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);


    @Autowired
    private CommonserviceImpl commonservice;

    @Autowired
    private CostDetailSelfMapper costDetailMapper;

    @Autowired
    private UserDetailFactory userDetailFactory;

    @Autowired
    private ScoreDetailService scoreDetailService;

    @Override
    public <T> int createCostDetail(T t, String orderNumber, HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        CostDetail costDetail = new CostDetail();
        // 支付宝支付
        AlipayRequest alipayRequest = (AlipayRequest)t;
        costDetail.setCost(new BigDecimal(alipayRequest.getTotalAmount()));
        costDetail.setCreateTime(new Date());
        costDetail.setType(OrderTypeEnum.getName(alipayRequest.getType()));
        costDetail.setStatus(1);
        costDetail.setPayType("ZHIFUBAO");
        costDetail.setUserId(user.getId());
        if(alipayRequest.getType() == 1){
            costDetail.setSubjectId(alipayRequest.getConditionId());
        }else if(alipayRequest.getType() == 2){
            costDetail.setMonth(alipayRequest.getConditionId());
        }
        costDetail.setOrderNumber(orderNumber);
        return costDetailMapper.insertSelective(costDetail);
    }

    @Override
    public String getAlipayOrder(AlipayRequest alipayRequest,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpResponse) {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        BeanUtils.copyProperties(alipayRequest,model);
        String orderId = new Date().getTime() + "" + (int)((Math.random()*9+1)*1000) + "z";
        model.setOutTradeNo(orderId);
        model.setProductCode("QUICK_WAP_PAY");      // 默认写死
        request.setBizModel(model);
        request.setNotifyUrl(url + NOTIFY_URL);   // 验证前面的url
        request.setReturnUrl(url + alipayRequest.getSuccessHtml());   // 回调的url地址,即付款成功页
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            createCostDetail(alipayRequest,orderId,httpServletRequest);
            httpResponse.setContentType("text/html;charset=" + CHARSET);
            httpResponse.getWriter().write(response.getBody());//直接将完整的表单html输出到页面
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return null;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String notifyAlipay(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        logger.info("支付宝支付结果通知"+requestParams.toString());
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            logger.info(name + "-----------------" + valueStr);
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_KEY, CHARSET,"RSA2");
        logger.info("---------------支付结果--------------" + flag);
        String orderNumber = params.get("out_trade_no");
        if(flag){
            CostDetail costDetail = editCostStatus(orderNumber,2);
            // 操作subject_detail或者vip_detail 表
            UserDetailService userDetailService = userDetailFactory.getFactory(costDetail);
            userDetailService.createUserDetail(costDetail);
            scoreDetailService.addScore(costDetail.getUserId(),1,
                    costDetail.getType().compareTo("1") == 0? ScoreEnum.getName(5):ScoreEnum.getName(6));
        }else{
            editCostStatus(orderNumber,5);
        }
        return "success";
    }
}
