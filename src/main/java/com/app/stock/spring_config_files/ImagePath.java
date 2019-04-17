package com.app.stock.spring_config_files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lmx
 * Date 2018/12/24
 * 静态资源文件上传相关配置
 */
@Component
public class ImagePath {

    @Value("${image.path.avatar}")
    private String imagePathAvatar;

    @Value("${image.path.advertise}")
    private String imagePathAdvertise;

    @Value("${image.path.subject}")
    private String imagePathSubject;

    @Value("${image.path.vedio}")
    private String imagePathVedio;

    public String getImagePathAvatar() {
        return imagePathAvatar;
    }

    public void setImagePathAvatar(String imagePathAvatar) {
        this.imagePathAvatar = imagePathAvatar;
    }

    public String getImagePathAdvertise() {
        return imagePathAdvertise;
    }

    public void setImagePathAdvertise(String imagePathAdvertise) {
        this.imagePathAdvertise = imagePathAdvertise;
    }

    public String getImagePathVedio() {
        return imagePathVedio;
    }

    public void setImagePathVedio(String imagePathVedio) {
        this.imagePathVedio = imagePathVedio;
    }

    public String getImagePathSubject() {
        return imagePathSubject;
    }

    public void setImagePathSubject(String imagePathSubject) {
        this.imagePathSubject = imagePathSubject;
    }
}
