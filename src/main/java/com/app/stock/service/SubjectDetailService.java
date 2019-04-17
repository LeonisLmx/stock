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

    public int deleteVideo(Long id);

    public Map<String,Object> uploadVideo(MultipartFile file,Map<String,String> map) throws IOException;

    public Map<String,Object> uploadVideo(MultipartFile file);

    public List videoList();
}
