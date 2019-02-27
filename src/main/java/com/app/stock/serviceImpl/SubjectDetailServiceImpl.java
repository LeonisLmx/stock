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
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private AdvertiseService advertiseService;

    @Autowired
    private ImagePath imagePath;

    @Override
    public int deleteVideo(List<Long> list) {
        return subjectDetailSelfMapper.batchUpdate(list);
    }

    @Override
    public Map<String,Object> uploadVideo(Map<String, String> map) throws IOException {
        Map<String,Object> pathMap = advertiseService.uploadImage(3,map.get("url"));
        String url = pathMap.get("url").toString();
        if(StringUtils.isBlank(map.get("id"))){
            File source = new File(url);
            String timeLength = ReadVideoTime(source);
            SubjectDetail subjectDetail = new SubjectDetail();
            subjectDetail.setVedioUrl(url);
            subjectDetail.setSubjectId(Long.valueOf(map.get("subject_id")));
            subjectDetail.setTitle(map.get("title"));
            subjectDetail.setCreateTime(new Date());
            subjectDetailSelfMapper.insertSelective(subjectDetail);
        }else{
            SubjectDetail subjectDetail = subjectDetailSelfMapper.selectByPrimaryKey(Long.valueOf(map.get("id")));
            subjectDetail.setVedioUrl(url);
            subjectDetailSelfMapper.updateByPrimaryKeySelective(subjectDetail);
        }
        return pathMap;
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = new Date().getTime() + "-_-" + file.getOriginalFilename();
        String filePath = imagePath.getImagePathVedio();
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            logger.info("上传成功");
            System.out.println(fileName);
            return "/video/" + fileName;
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }
        return "上传失败！";
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
            length = hour+"'"+minute+"''"+second+"'''";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
}
