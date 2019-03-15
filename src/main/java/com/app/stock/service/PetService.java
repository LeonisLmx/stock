package com.app.stock.service;

import com.app.stock.model.Pet;
import com.app.stock.model.request.FeedPetRequest;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/29
 */
public interface PetService {

    public boolean createPet(String name,HttpServletRequest request);

    public String feedPet(FeedPetRequest map, HttpServletRequest request) throws ParseException;

    public Map<String,Object> petInfo(HttpServletRequest request);

    public String resetPet(HttpServletRequest request);

    public String saleStock(FeedPetRequest map, HttpServletRequest request) throws ParseException;

    public Boolean isHavePeet(Long userId);

    public Boolean isTrading() throws ParseException;
}
