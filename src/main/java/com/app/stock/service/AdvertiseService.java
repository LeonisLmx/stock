package com.app.stock.service;

import com.app.stock.model.Advertise;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/26
 */
public interface AdvertiseService {

    public Map<String,Object> uploadImage(Integer type, String imgStr) throws IOException;

    public int uploadAdvertise(String imgStr, Advertise advertise) throws IOException;

    public List<Map<String,Object>> list();

    public int deleteAdvertise(Long id);
}
