package com.app.stock.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/23
 */
public interface AdminService {

    @Transactional(propagation = Propagation.REQUIRED)
    public int shieldUser(List<Object> userList,Long isShield);
}
