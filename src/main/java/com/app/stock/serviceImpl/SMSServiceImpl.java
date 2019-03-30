package com.app.stock.serviceImpl;

import com.app.stock.common.CommonUtil;
import com.app.stock.common.HttpClientRequest;
import com.app.stock.common.RedisUtil;
import com.app.stock.common.commonEnum.SMSEnum;
import com.app.stock.config.redis.RedisExecutor;
import com.app.stock.service.SMSSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author mingxin Liu
 * @Date 2019-03-30 22:23
 * @Description //阿里云SMS service实现类
 */
@Service
public class SMSServiceImpl implements SMSSerivce {

    private static Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    private static String SMSURL = "https://dysmsapi.aliyuncs.com";
    private static String ACCESS_SECRET = "ZAi8br4mbZ9xz4ZNwAtJcgXTv42hoO";
    private static String ACCESS_KEY = "LTAIawlYkWl1MXwC";
    private static String SIGN_NAME = "七星汇";

    public static String specialUrlEncode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }

    public static String sign(String accessSecret, String stringToSign) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }

    // 阿里云短信发送
    @Override
    public Object sendSMS(String templateCode,String phone) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        Map<String, String> paras = new HashMap<String, String>();
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        String randomId = UUID.randomUUID().toString();
        System.out.println(randomId);
        paras.put("SignatureNonce",randomId);
        paras.put("AccessKeyId", ACCESS_KEY);
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new Date()));
        paras.put("Format", "XML");
        // 2.业务参数
        paras.put("Action", "SendSms");
        paras.put("Version", "2017-05-25");
        paras.put("RegionId", "cn-hangzhou");
        paras.put("PhoneNumbers",phone);
        paras.put("SignName",SIGN_NAME);
        paras.put("TemplateCode", templateCode);
        // 生成6位随机数
        String code = ((Math.random()*9+1)*100000) + "";
        paras.put("TemplateParam", "{\"code\":"+ code +"}");
        // 3. 去除签名关键字Key
        if (paras.containsKey("Signature"))
            paras.remove("Signature");
        // 4. 参数KEY排序
        TreeMap<String, String> sortParas = new TreeMap<String, String>();
        sortParas.putAll(paras);
        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));
        String sign = sign(ACCESS_SECRET + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        String signature = specialUrlEncode(sign);
        String url = SMSURL + "?Signature=" + signature + sortQueryStringTmp;
        String response =  HttpClientRequest.Get(url);
        logger.info(response);
        redisUtil.set(phone + templateCode,code,5L, TimeUnit.MINUTES);
        return code;
    }
}
