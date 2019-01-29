package com.app.stock.service;

import com.app.stock.model.request.PrimarykeyIdRequest;
import com.app.stock.model.request.SubjectListRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/15
 */
public interface SubjectService {

    public Map<String,Object> list(SubjectListRequest subjectListRequest);

    public Map<String,Object> isPay(PrimarykeyIdRequest isPayRequest, HttpServletRequest request);

    public Map<String,Object> detail(PrimarykeyIdRequest isPayRequest);
}
