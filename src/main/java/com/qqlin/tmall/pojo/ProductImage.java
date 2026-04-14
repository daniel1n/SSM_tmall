package com.qqlin.tmall.pojo;

/**
 * ProductImage entity representing an image associated with a product.
 * Images are categorized by type: "single" images for listing pages,
 * "detail" images for the product detail page.
 */
public class ProductImage {
    /** Unique identifier for the product image */
    private Integer id;

    /** Foreign key to the product this image belongs to */
    private Integer pid;

    /** Image type: "single" for thumbnail/listing images, "detail" for detail page images */
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}