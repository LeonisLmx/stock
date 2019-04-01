package com.app.stock.config;

import com.alibaba.druid.support.json.JSONUtils;
import com.app.stock.common.encrypt.MD5Utils;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/28
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    private final String[] whiteUrl = {
            "/api/user/get_message_code",
            "/api/user/register",
            "/api/user/login/password",
            "/api/user/login/message_code",
            "/api/user/find_password",
            "/api/alipay/alipayNotify",
            "/api/advertise/list",
            "/api/advertise/publish",
            "/api/comment/get_comment",
            "/api/subject/type_list",
            "/api/subject/list",
            "/api/subject/video_list",
            "/api/http/test",
            "/error"
    };

    @Autowired
    private UserSelfMapper userMapper;

    private boolean isWhiteUrl(String url){
        for(String str:whiteUrl){
            if(url.contains(str)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String url = request.getRequestURI();
        if(!isWhiteUrl(url)) {
            if (StringUtils.isBlank(token)) {
                returnJson(response, "token不能为空",1);
                return false;
            }
            if (userMapper.selectByPrimaryToken(null, token) == null) {
                returnJson(response, "token已失效",3);
                return false;
            }
            if(url.contains("/api/admin")) {
                User user = userMapper.selectByPrimaryToken(null, token);
                if(user.getIsAdmin() == 0){
                    returnJson(response, "操作无权限",1);
                    return false;
                }
            }
        }
        if(request.getParameterMap().size() > 0){
            // 拦截URl参数请求，在切面拦截BODY请求参数
            Map<String,String[]> map = request.getParameterMap();
            Map<String,Object> signMap = new HashMap<>();
            for(Map.Entry<String,String[]> entry:map.entrySet()){
                if(!entry.getKey().equals("sign")){
                    signMap.put(entry.getKey(),entry.getValue());
                }
            }
            if(map.get("sign") != null && !MD5Utils.sign(signMap).equals(Arrays.toString(map.get("sign")))){
                returnJson(response, "校验参数有误",1);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    protected void returnJson(HttpServletResponse response,String json,Integer code){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try{
            writer = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("data",new HashMap<>());
            map.put("err_code",code);
            map.put("reason",json);
            writer.print(JSONUtils.toJSONString(map));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(writer != null ){
                writer.close();
            }
        }
    }
}
