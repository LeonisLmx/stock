package com.app.stock.config;

import com.app.stock.common.CommonUtil;
import com.app.stock.common.Response;
import com.app.stock.common.encrypt.MD5Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/7
 */
@Aspect
@Component
public class RequestAop {

    private static Logger logger = LoggerFactory.getLogger(RequestAop.class);

    @Pointcut("execution(public * com.app.stock.controller.*.*(..))")
    public void checkRequest() {
    }

    @Pointcut("@annotation(com.app.stock.annoation.PostVerify)")
    public void annoationPoint(){};

    @Before("checkRequest() && annoationPoint()")
    public void before(JoinPoint joinPoint) throws Exception {
        Map<String, Object> map;
        if(joinPoint.getArgs()[0] instanceof Map){
            map = (Map) joinPoint.getArgs()[0];
        }else{
            map = CommonUtil.beanToMap(joinPoint.getArgs()[0]);
        }
        Map<String,Object> signMap = new LinkedHashMap<>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(!entry.getKey().equals("sign")){
                signMap.put(entry.getKey(),entry.getValue());
            }
        }
        if(map.get("sign") != null && !MD5Utils.sign(signMap).equals(map.get("sign").toString())){
            // 抛出异常，去上一层截取
            throw new VerifyException("");
        }
    }

    @Around("checkRequest()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Response response = new Response();
        response.setReason("操作成功");
        response.setData(new HashMap<>());
        try{
            Object object = pjp.proceed();
            if(object == null || object instanceof Response) {
                response = (Response) object;
            }else{
                return object.toString();
            }
        }catch (VerifyException e){
            response.setReason("校验失败");
            response.setError_code(1);
        }catch (Exception e){
            logger.error("异常信息",e);
            response.setReason("系统异常");
            response.setError_code(1);
        }
        return response;
    }
}
