package com.app.stock.serviceImpl;

import com.app.stock.mapper.SubjectTypeSelfMapper;
import com.app.stock.mapper.auto_generate.SubjectTypeMapper;
import com.app.stock.service.SubjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {

    @Autowired
    private SubjectTypeSelfMapper subjectTypeSelfMapper;

    @Override
    public List<Map<String, Object>> typeList() {
        return subjectTypeSelfMapper.typeList();
    }
}
