package com.app.stock.serviceImpl;

import com.app.stock.mapper.AppversionSelfMapper;
import com.app.stock.mapper.auto_generate.AppVersionMapper;
import com.app.stock.model.AppVersion;
import com.app.stock.model.request.AppVersionAddRequest;
import com.app.stock.model.request.AppVersionSearchRequest;
import com.app.stock.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author mingxin Liu
 * @Date 2019-03-23 23:11
 * @Description //version 相关实现类
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppversionSelfMapper appVersionMapper;

    @Override
    public Integer addVersion(AppVersionAddRequest appVersionAddRequest) {
        AppVersion appVersion = new AppVersion();
        // 如果存在就不返回-1
        if(appVersionMapper.selectIsHave(appVersionAddRequest.getVersion()) > 0){
            return -1;
        }
        BeanUtils.copyProperties(appVersionAddRequest,appVersion);
        appVersion.setCreateTime(new Date());
        return appVersionMapper.insertSelective(appVersion);
    }

    @Override
    public Integer editVersion(AppVersion appVersion) {
        // 如果存在就不返回-1
        if(appVersionMapper.selectIsHave(appVersion.getVersion()) > 0){
            return -1;
        }
        return appVersionMapper.updateByPrimaryKeySelective(appVersion);
    }

    @Override
    public List<Map<String,Object>> searchList(AppVersionSearchRequest appVersion) {
        if(appVersion == null){
            appVersion = new AppVersionSearchRequest();
        }
        PageHelper.startPage(appVersion.getPage(),appVersion.getPage_size());
        return appVersionMapper.selectListByCondition(appVersion);
    }
}
