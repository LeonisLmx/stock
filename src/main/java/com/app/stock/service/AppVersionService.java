package com.app.stock.service;

import com.app.stock.model.AppVersion;
import com.app.stock.model.request.AppVersionAddRequest;
import com.app.stock.model.request.AppVersionSearchRequest;

import java.util.List;
import java.util.Map;

/**
 * @Author mingxin Liu
 * @Date 2019-03-23 23:09
 * @Description //version 相关操作接口
 */
public interface AppVersionService {

    // 添加版本
    public Integer addVersion(AppVersionAddRequest appVersionAddRequest);

    // 修改版本
    public Integer editVersion(AppVersion appVersion);

    // 查看历史版本
    public List<Map<String,Object>> searchList(AppVersionSearchRequest appVersion);
}
