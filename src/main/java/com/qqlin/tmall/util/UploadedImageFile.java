package com.qqlin.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * Wrapper class for uploaded multipart image files in Spring MVC.
 *
 * <p>Provides a simpler interface for handling image uploads in controllers
 * by wrapping the Spring MultipartFile. This class is typically used as a
 * form parameter in controller methods handling file uploads.</p>
 *
 * <p>Usage example in controller:</p>
 * <pre>
 * public String addProduct(@RequestParam("image") UploadedImageFile file, ...) {
 *     File image = new File(uploadDir, file.getImage().getOriginalFilename());
 *     file.getImage().transferTo(image);
 * }
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-18
 * @see org.springframework.web.multipart.MultipartFile
 */
public class UploadedImageFile {
    /** The underlying multipart file being wrapped */
    MultipartFile image;

    /**
     * Gets the underlying multipart file.
     *
     * @return the MultipartFile instance
     */
    public MultipartFile getImage() {
        return image;
    }

    /**
     * Sets the multipart file to wrap.
     *
     * @param image the MultipartFile to wrap
     */
    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
