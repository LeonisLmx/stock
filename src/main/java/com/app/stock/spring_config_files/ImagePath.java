package com.app.stock.spring_config_files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lmx
 * Date 2018/12/24
 */
@Component
public class ImagePath {

    @Value("${image.path.avatar}")
    private String imagePathAvatar;

    @Value("${image.path.advertise}")
    private String imagePathAdvertise;

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
}
