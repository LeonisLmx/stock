package com.app.stock.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/31
 */
public interface SubjectDetailService {

    public int deleteVideo(List<Long> list);

    public Map<String,Object> uploadVideo(Map<String,String> map) throws IOException;

    public String uploadVideo(MultipartFile file);
}
