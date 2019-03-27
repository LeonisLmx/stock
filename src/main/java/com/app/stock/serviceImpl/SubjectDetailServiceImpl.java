package com.app.stock.serviceImpl;

import com.app.stock.mapper.SubjectDetailSelfMapper;
import com.app.stock.model.SubjectDetail;
import com.app.stock.service.AdvertiseService;
import com.app.stock.service.SubjectDetailService;
import com.app.stock.spring_config_files.ImagePath;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by lmx
 * Date 2019/1/31
 */
@Service
public class SubjectDetailServiceImpl implements SubjectDetailService {

    private static Logger logger  = LoggerFactory.getLogger(SubjectDetailServiceImpl.class);

    @Autowired
    private SubjectDetailSelfMapper subjectDetailSelfMapper;

    @Autowired
    private ImagePath imagePath;

    @Override
    public int deleteVideo(List<Long> list) {
        return subjectDetailSelfMapper.batchUpdate(list);
    }

    @Override
    public Map<String,Object> uploadVideo(MultipartFile file,Map<String, String> map) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> uploadMap = new HashMap<>();
        String timeLength = "";
        if(file != null){
            uploadMap = uploadVideo(file);
            if(!(Boolean)uploadMap.get("status")){
                return uploadMap;
            }
            File source = new File(imagePath.getImagePathVedio() + uploadMap.get("name"));
            timeLength = ReadVideoTime(source);
        }else{
            resultMap.put("status",false);
            return resultMap;
        }
        if(StringUtils.isBlank(map.get("id"))){
            SubjectDetail subjectDetail = new SubjectDetail();
            subjectDetail.setVedioUrl("/video/" + uploadMap.get("name"));
            subjectDetail.setFileName(uploadMap.get("fileName").toString());
            subjectDetail.setDuration(timeLength);
            subjectDetail.setSubjectId(Long.valueOf(map.get("subject_id")));
            if(map.get("title") != null) {
                subjectDetail.setTitle(map.get("title"));
            }
            subjectDetail.setCreateTime(new Date());
            subjectDetailSelfMapper.insertSelective(subjectDetail);
        }else{
            SubjectDetail subjectDetail = subjectDetailSelfMapper.selectByPrimaryKey(Long.valueOf(map.get("id")));
            subjectDetail.setVedioUrl("/video/" + uploadMap.get("name"));
            subjectDetail.setDuration(timeLength);
            subjectDetail.setFileName(uploadMap.get("fileName").toString());
            if(map.get("title") != null) {
                subjectDetail.setTitle(map.get("title"));
            }
            if(map.get("subject_id") != null){
                subjectDetail.setSubjectId(Long.valueOf(map.get("subject_id")));
            }
            subjectDetail.setModifyTime(new Date());
            subjectDetailSelfMapper.updateByPrimaryKeySelective(subjectDetail);
        }
        resultMap.put("status",true);
        resultMap.put("path","/video/" + uploadMap.get("name"));
        return resultMap;
    }

    @Override
    public Map<String,Object> uploadVideo(MultipartFile file) {
        Map<String,Object> map = new HashMap<>();
        map.put("status",true);
        if (file.isEmpty()) {
            map.put("status",false);
            return map;
        }
        map.put("fileName",file.getOriginalFilename());
        String format = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + new Date().getTime() + format;
        String filePath = imagePath.getImagePathVedio();
        File files = new File(filePath);
        // 如果不存在则新建
        if(!files.exists()){
            files.mkdirs();
        }
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            logger.info("上传成功");
            map.put("name",fileName);
            return map;
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }
        return map;
    }

    private String ReadVideoTime(File source) {
        Encoder encoder = new Encoder();
        String length = "";
        try {
            MultimediaInfo m = encoder.getInfo(source);
            long ls = m.getDuration()/1000;
            int hour = (int) (ls/3600);
            int minute = (int) (ls%3600)/60;
            int second = (int) (ls-hour*3600-minute*60);
            String hourTime = hour < 10? "0" + hour:hour + "";
            String minuteTime = minute < 10? "0" + minute:minute + "";
            String secondTime = second < 10? "0" + second:second + "";
            length = hourTime + ":" + minuteTime + ":" + secondTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
}
