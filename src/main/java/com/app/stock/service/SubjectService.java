package com.app.stock.service;

import com.app.stock.model.Subject;
import com.app.stock.model.request.PrimarykeyIdRequest;
import com.app.stock.model.request.SubjectListRequest;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/15
 */
public interface SubjectService {

    public List<Map<String,Object>> list(SubjectListRequest subjectListRequest);

    public Map<String,Object> isPay(PrimarykeyIdRequest isPayRequest, HttpServletRequest request);

    public Map<String,Object> detail(PrimarykeyIdRequest isPayRequest);

    public void addSubject(Subject subject);

    public int editSubject(Subject subject);
}
