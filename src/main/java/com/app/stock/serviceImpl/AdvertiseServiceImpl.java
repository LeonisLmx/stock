package com.app.stock.serviceImpl;

import com.app.stock.mapper.AdvertiseSelfMapper;
import com.app.stock.model.Advertise;
import com.app.stock.service.AdvertiseService;
import com.app.stock.spring_config_files.ImagePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.*;

/**
 * Created by lmx
 * Date 2018/12/24
 */
@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    private ImagePath imagePath;

    @Autowired
    private AdvertiseSelfMapper advertiseSelfMapper;

    @SuppressWarnings("all")
    public Map<String,Object> uploadImage(Integer type,String imgStr) throws IOException {
        String style = imgStr.substring(imgStr.indexOf("/") + 1,imgStr.indexOf(";"));
        imgStr = imgStr.substring(imgStr.indexOf(";base64,") + 8);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        Map<String,Object> map = new HashMap<>();
        String path = "";
        String url = "";
        if(type == 1){
            path = imagePath.getImagePathAvatar();
            url = "/avatar/";
        }else if(type == 2){
            path = imagePath.getImagePathAdvertise();
            url = "/advertise/";
        }
        String fileName = new Date().getTime() + "_" + UUID.randomUUID().toString() + "." + style;
        String imgPath = path + fileName;
        OutputStream os = null;
        try {
            os = new FileOutputStream(imgPath);
            os.write(data);
            os.flush();
            os.close();
            map.put("url",url + fileName);
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int uploadAdvertise(String imgStr, Advertise advertise) throws IOException {
        String filePath = uploadImage(2,imgStr).get("url").toString();
        advertise.setImageUrl(filePath);
        advertise.setCreateTime(new Date());
        return advertiseSelfMapper.insertSelective(advertise);
    }

    @Override
    public List<Map<String,Object>> list() {
        return advertiseSelfMapper.selectList();
    }
}
