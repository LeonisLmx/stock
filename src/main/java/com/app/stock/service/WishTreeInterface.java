package com.app.stock.service;

import com.app.stock.model.WishTree;
import com.app.stock.model.request.WishListRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/2/24
 */
public interface WishTreeInterface {

    public List<Map<String,Object>> getAllInfo(HttpServletRequest request, WishListRequest wishListRequest);

    public Map<String,Object> getAloneWish(Long Id);

    public int operateWish(WishTree WishTree,HttpServletRequest request);

    public int deleteWish(Long id);
}
