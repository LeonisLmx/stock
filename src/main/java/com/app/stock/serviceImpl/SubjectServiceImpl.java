package com.app.stock.serviceImpl;

import com.app.stock.mapper.SubjectDetailSelfMapper;
import com.app.stock.mapper.SubjectSelfMapper;
import com.app.stock.mapper.UserSubjectDetailSelfMapper;
import com.app.stock.model.Subject;
import com.app.stock.model.User;
import com.app.stock.model.request.PrimarykeyIdRequest;
import com.app.stock.model.request.SubjectListRequest;
import com.app.stock.service.SubjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/15
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectSelfMapper subjectSelfMapper;

    @Autowired
    private CommonserviceImpl commonservice;

    @Autowired
    private UserSubjectDetailSelfMapper userSubjectDetailSelfMapper;

    @Autowired
    private SubjectDetailSelfMapper subjectDetailSelfMapper;

    @Override
    public List<Map<String, Object>> list(SubjectListRequest subjectListRequest) {
        PageHelper.startPage(subjectListRequest.getPage(),subjectListRequest.getPage_size());
        List<Map<String,Object>> list = subjectSelfMapper.list(subjectListRequest.getType(),subjectListRequest.getIsFree(),
                subjectListRequest.getOrderType(),subjectListRequest.getOrder());
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo.getList();
    }

    @Override
    public Map<String,Object> isPay(PrimarykeyIdRequest isPayRequest, HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        int count = userSubjectDetailSelfMapper.selectIsPayBySubjectId(user.getId(),isPayRequest.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("data",count == 0?false:true);
        return map;
    }

    @Override
    public Map<String, Object> detail(PrimarykeyIdRequest isPayRequest) {
        Map<String,Object> map = subjectSelfMapper.detail(isPayRequest.getId());
        List<Map<String,Object>> vedioList = subjectDetailSelfMapper.vedioList(isPayRequest.getId());
        map.put("list",vedioList);
        return map;
    }

    @Override
    public void addSubject(Subject subject) {
        subject.setCreateTime(new Date());
        subjectSelfMapper.insertSelective(subject);
    }

    @Override
    public int editSubject(Subject subject) {
        return subjectSelfMapper.updateByPrimaryKeySelective(subject);
    }
}
