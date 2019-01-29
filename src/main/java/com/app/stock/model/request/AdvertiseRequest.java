package com.app.stock.model.request;

import com.app.stock.model.Advertise;

/**
 * Created by lmx
 * Date 2019/1/25
 */
public class AdvertiseRequest extends Advertise {

    private String imgStr;

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }
}
