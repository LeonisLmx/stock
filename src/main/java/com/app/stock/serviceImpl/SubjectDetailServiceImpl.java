package com.app.stock.serviceImpl;

import com.app.stock.mapper.SubjectDetailSelfMapper;
import com.app.stock.model.SubjectDetail;
import com.app.stock.service.AdvertiseService;
import com.app.stock.service.SubjectDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
@Service
public class SubjectDetailServiceImpl implements SubjectDetailService {

    @Autowired
    private SubjectDetailSelfMapper subjectDetailSelfMapper;

    @Autowired
    private AdvertiseService advertiseService;

    @Override
    public int deleteVideo(List<Long> list) {
        return subjectDetailSelfMapper.batchUpdate(list);
    }

    @Override
    public Map<String,Object> uploadVideo(Map<String, String> map) throws IOException {
        Map<String,Object> pathMap = advertiseService.uploadImage(3,map.get("url"));
        String url = pathMap.get("url").toString();
        if(StringUtils.isBlank(map.get("id"))){
            SubjectDetail subjectDetail = new SubjectDetail();
            subjectDetail.setVedioUrl(url);
            subjectDetail.setSubjectId(Long.valueOf(map.get("subject_id")));
            subjectDetail.setCreateTime(new Date());
            subjectDetailSelfMapper.insertSelective(subjectDetail);
        }else{
            SubjectDetail subjectDetail = subjectDetailSelfMapper.selectByPrimaryKey(Long.valueOf(map.get("id")));
            subjectDetail.setVedioUrl(url);
            subjectDetailSelfMapper.updateByPrimaryKeySelective(subjectDetail);
        }
        return pathMap;
    }
}
