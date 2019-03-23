package com.app.stock.model.request;

import com.app.stock.model.AppVersion;

/**
 * @Author mingxin Liu
 * @Date 2019-03-24 00:05
 * @Description //TODO
 */
public class AppVersionSearchRequest extends AppVersion {

    private Integer page;
    private Integer page_size;

    public Integer getPage() {
        return page  == null?1:page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage_size() {
        return page_size == null?20:page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
