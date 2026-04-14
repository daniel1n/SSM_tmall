package com.qqlin.tmall.pojo;

import java.util.Date;
import java.util.List;

/**
 * Product entity representing a item for sale in the e-commerce system.
 * Products belong to a Category and have multiple ProductImages for visual display.
 * Each product has pricing, stock management, and can have property values that describe its specifications.
 */
public class Product {
    /** Unique identifier for the product */
    private Integer id;

    /** Product name displayed to users */
    private String name;

    /** Secondary title/subtitle for the product, often used for promotional descriptions */
    private String subTitle;

    /** Original price before any discounts or promotions */
    private Float originalPrice;

    /** Promotional price, typically set during sales events */
    private Float promotePrice;

    /** Current inventory count available for sale */
    private Integer stock;

    /** Foreign key to the category this product belongs to */
    private Integer cid;

    /** Date when the product was created/added to the system */
    private Date createDate;

    /**
     * Non-database field: Associated Category object for display purposes
     */
    private Category category;

    /** First/primary product image for thumbnail display */
    private ProductImage firstProductImage;

    /** Single images for product listing pages (type="single") */
    private List<ProductImage> productSingleImages;

    /** Detail images for product detail page (type="detail") */
    private List<ProductImage> productDetailImages;

    /** Total number of units sold */
    private int saleCount;

    /** Total number of customer reviews */
    private int reviewCount;

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}