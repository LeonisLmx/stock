package com.app.stock.serviceImpl;

import com.app.stock.mapper.SubjectSelfMapper;
import com.app.stock.mapper.TeacherSelfMapper;
import com.app.stock.mapper.auto_generate.TeacherMapper;
import com.app.stock.model.Teacher;
import com.app.stock.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherSelfMapper teacherSelfMapper;

    @Autowired
    private SubjectSelfMapper subjectSelfMapper;

    @Override
    public List<Map<String, Object>> teacherList() {
        List<Map<String,Object>> list = teacherSelfMapper.teacherList();
        return list;
    }

    @Override
    public int newTeacher(Teacher teacher) {
        return teacherSelfMapper.insertSelective(teacher);
    }

    @Override
    public int editTeacher(Teacher teacher) {
        return teacherSelfMapper.updateByPrimaryKeySelective(teacher);
    }

    @Override
    public int deleteTeacher(List<Long> ids) {
        teacherSelfMapper.batchDelete(ids);
        subjectSelfMapper.batchUpdateSubjectTeacher(ids);
        return 1;
    }
}
