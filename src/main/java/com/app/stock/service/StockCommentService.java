package com.app.stock.service;

import com.app.stock.model.StockComment;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/28
 */
public interface StockCommentService {

    public int publishComment(StockComment stockComment, HttpServletRequest request);

    public Map<String,Object> getComment(Integer type, String stockCode, String stockMarket,String date,Integer page,Integer page_size,HttpServletRequest request);
}
