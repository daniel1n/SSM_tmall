package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.ProductImage;

import java.util.List;

/**
 * Service interface for ProductImage business operations.
 * Manages product images including single preview images and detail images.
 *
 * @author qqlin
 * @date 2020-5-20
 */
public interface ProductImageService {
    /**
     * Image type constant for single/preview images (used in product listing).
     */
    String type_single = "type_single";

    /**
     * Image type constant for detail images (used in product detail page).
     */
    String type_detail = "type_detail";

    /**
     * Add a new product image.
     *
     * @param pi ProductImage entity to add (must have valid product via pid)
     */
    void add(ProductImage pi);

    /**
     * Delete a product image by ID.
     *
     * @param id ProductImage ID to delete
     */
    void delete(int id);

    /**
     * Update an existing product image.
     *
     * @param pi ProductImage entity with updated values (ID must exist)
     */
    void update(ProductImage pi);

    /**
     * Get a single product image by ID.
     *
     * @param id ProductImage ID
     * @return ProductImage entity, or null if not found
     */
    ProductImage get(int id);

    /**
     * Get all product images for a specific product and image type.
     *
     * @param pid  Product ID to filter images
     * @param type Image type (type_single or type_detail)
     * @return List of product images matching the criteria
     */
    List list(int pid, String type);
}
