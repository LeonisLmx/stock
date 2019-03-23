package com.app.stock.model.request;

import javax.validation.constraints.NotNull;

/**
 * @Author mingxin Liu
 * @Date 2019-03-23 23:05
 * @Description //TODO
 */
public class AppVersionAddRequest extends VerifyRequest {

    @NotNull(message = "版本号不能为空")
    private String version;
    @NotNull(message = "描述不能为空")
    private String description;
    @NotNull(message = "版本类型不能为空")
    private int type;
    @NotNull(message = "版本链接不能为空")
    private String linkUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
