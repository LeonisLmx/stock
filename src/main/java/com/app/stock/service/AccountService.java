package com.app.stock.service;

import com.app.stock.model.AccountBook;
import com.app.stock.model.request.AccountListRequest;
import com.app.stock.model.request.AccountSummarizeRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/4
 */
public interface AccountService {

    public String addNewAccount(AccountBook accountBook, HttpServletRequest request);

    public String deleteAccount(Long id);

    public String editAccount(AccountBook accountBook);

    public Map<String, Object> list(AccountListRequest accountListRequest);

    public List<Map<String,Object>> monthList(AccountSummarizeRequest accountSummarizeRequest,HttpServletRequest request);

    public List<Map<String,Object>> yearList(AccountSummarizeRequest accountSummarizeRequest,HttpServletRequest request);
}
