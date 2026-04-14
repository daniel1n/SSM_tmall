package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * Service interface for PropertyValue business operations.
 * Handles product-specific property value management (e.g., color: red, size: XL).
 *
 * @author qqlin
 * @date 2020-5-20
 */
public interface PropertyValueService {
    /**
     * Initialize property values for a product.
     * Creates PropertyValue entries for all properties in the product's category
     * that do not already have a value for this product.
     *
     * @param product Product to initialize property values for (category must be loaded)
     */
    void init(Product product);

    /**
     * Update an existing property value.
     *
     * @param propertyValue PropertyValue entity with updated value (ID must exist)
     */
    void update(PropertyValue propertyValue);

    /**
     * Get a property value by property ID and product ID.
     *
     * @param ptid Property ID
     * @param pid  Product ID
     * @return PropertyValue entity, or null if not found
     */
    PropertyValue get(int ptid, int pid);

    /**
     * Get all property values for a specific product.
     *
     * @param pid Product ID to filter property values
     * @return List of property values for the specified product
     */
    List<PropertyValue> list(int pid);
}
