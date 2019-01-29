package com.app.stock.serviceImpl;

import com.app.stock.common.commonEnum.ScoreEnum;
import com.app.stock.mapper.AccountBookSelfMapper;
import com.app.stock.mapper.ScoreDetailSelfMapper;
import com.app.stock.mapper.StoreTypeSelfMapper;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.model.AccountBook;
import com.app.stock.model.ScoreDetail;
import com.app.stock.model.StoreType;
import com.app.stock.model.User;
import com.app.stock.model.request.AccountListRequest;
import com.app.stock.model.request.AccountSummarizeRequest;
import com.app.stock.service.AccountService;
import com.app.stock.service.ScoreDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lmx
 * Date 2019/1/4
 */
@Service
public class AccountServiceImpl  implements AccountService {

    @Autowired
    private CommonserviceImpl commonservice;

    @Autowired
    private AccountBookSelfMapper accountBookSelfMapper;

    @Autowired
    private StoreTypeSelfMapper storeTypeSelfMapper;

    @Autowired
    private ScoreDetailSelfMapper scoreDetailSelfMapper;

    @Autowired
    private UserSelfMapper userSelfMapper;

    @Autowired
    private ScoreDetailService scoreDetailService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String addNewAccount(AccountBook accountBook, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StoreType storeType = storeTypeSelfMapper.selectByPrimaryKey(accountBook.getStoreTypeId());
        if(storeType != null) {
            User user = commonservice.getCurrentInfo(request);
            accountBook.setSerialNumber(new Date().getTime() + "" + user.getId() + "" + accountBook.getStoreTypeId());
            accountBook.setUserId(user.getId());
            accountBook.setCreateTime(new Date());
            accountBookSelfMapper.insertSelective(accountBook);
            int come = storeType.getIncome();
            if (scoreDetailSelfMapper.selectScoreDetailIsHaveByMonthAndFrom(sdf.format(new Date()),
                    come == 1? ScoreEnum.getName(3):ScoreEnum.getName(4),user.getId()) == 0) {
                scoreDetailService.addScore(user.getId(),1,come == 1? ScoreEnum.getName(3):ScoreEnum.getName(4));
            }
            return "新增成功";
        }else{
            return "账单类型不存在";
        }
    }

    @Override
    public String deleteAccount(Long id) {
        AccountBook accountBook = accountBookSelfMapper.selectByPrimaryKey(id);
        if(accountBook == null){
            return "账单不存在";
        }
        accountBook.setIsDelete(1);
        accountBookSelfMapper.updateByPrimaryKey(accountBook);
        return "删除成功";
    }

    @Override
    public String editAccount(AccountBook accountBook) {
        AccountBook searEntity = accountBookSelfMapper.selectByPrimaryKey(accountBook.getId());
        if(searEntity == null){
            return "账单不存在";
        }
        BeanUtils.copyProperties(accountBook,searEntity);
        accountBookSelfMapper.updateByPrimaryKeySelective(searEntity);
        return "修改成功";
    }

    @Override
    public Map<String, Object> list(AccountListRequest accountListRequest) {
        Map<String,Object> map = new HashMap<>();
        if(accountListRequest.getStoreTypeParentId() != null){
            if(accountListRequest.getStoreTypeId() == null){
                accountListRequest.setStoreTypeId(new ArrayList<>());
            }
            accountListRequest.getStoreTypeId().addAll(storeTypeSelfMapper.selectAllIdsByParent(accountListRequest.getStoreTypeParentId()));
        }
        PageHelper.startPage(accountListRequest.getPage(),accountListRequest.getPage_size());
        List<Map<String,Object>> list = accountBookSelfMapper.selectAllAccountByCondition(accountListRequest);
        PageInfo pageInfo = new PageInfo(list);
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        map.put("page",accountListRequest.getPage());
        map.put("page_size",accountListRequest.getPage_size());
        return map;
    }

    @Override
    public List<Map<String,Object>> monthList(AccountSummarizeRequest accountSummarizeRequest,HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        if(accountSummarizeRequest.getStoreTypeParentId() != null){
            if(accountSummarizeRequest.getStoreTypeId() == null){
                accountSummarizeRequest.setStoreTypeId(new ArrayList<>());
            }
            accountSummarizeRequest.getStoreTypeId().addAll(storeTypeSelfMapper.selectAllIdsByParent(accountSummarizeRequest.getStoreTypeParentId()));
        }
        List<Map<String,Object>> list = accountBookSelfMapper.selectAllListByMonth(user.getId(),accountSummarizeRequest.getMonth(),accountSummarizeRequest.getStoreTypeId());
        return list;
    }

    @Override
    public List<Map<String,Object>> yearList(AccountSummarizeRequest accountSummarizeRequest,HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        if(accountSummarizeRequest.getStoreTypeParentId() != null){
            if(accountSummarizeRequest.getStoreTypeId() == null){
                accountSummarizeRequest.setStoreTypeId(new ArrayList<>());
            }
            accountSummarizeRequest.getStoreTypeId().addAll(storeTypeSelfMapper.selectAllIdsByParent(accountSummarizeRequest.getStoreTypeParentId()));
        }
        List<Map<String,Object>> list = accountBookSelfMapper.selectAllListByYear(user.getId(),accountSummarizeRequest.getYear(),accountSummarizeRequest.getStoreTypeId());
        return list;
    }
}
