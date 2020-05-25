package com.qqlin.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author qqlin
 * @date 2020-5-18
 */
public class UploadedImageFile {
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
