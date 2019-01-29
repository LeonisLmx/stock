package com.app.stock.serviceImpl;

import com.app.stock.mapper.StockCommentSelfMapper;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmx
 * Date 2019/1/23
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserSelfMapper userSelfMapper;

    @Autowired
    private StockCommentSelfMapper stockCommentSelfMapper;

    @Override
    public int shieldUser(List<Object> userList,Long isShield) {
        List<Long> lists = new ArrayList<>();
        userList.forEach(entity -> {
            lists.add(Long.valueOf(entity + ""));
        });
        userSelfMapper.updateUserIsShield(isShield,lists);
        stockCommentSelfMapper.updateStockComment(isShield == 0?0:1,lists);
        return 1;
    }
}
