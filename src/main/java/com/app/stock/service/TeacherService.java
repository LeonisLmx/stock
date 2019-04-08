package com.app.stock.service;

import com.app.stock.model.Teacher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
public interface TeacherService {

    public List<Map<String,Object>> teacherList();

    public int newTeacher(Teacher teacher) throws IOException;

    public int editTeacher(Teacher teacher) throws IOException;

    public int deleteTeacher(List<Long> ids);
}
