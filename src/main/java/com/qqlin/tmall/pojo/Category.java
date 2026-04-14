package com.qqlin.tmall.pojo;

import java.util.List;

/**
 * Category entity representing a product classification in the e-commerce system.
 * Categories organize products into hierarchical groups and define properties (specifications)
 * that products within them can have.
 * Examples: "Electronics", "Clothing", "Books", "Home & Garden"
 *
 * Categories are the top-level organization unit for products in the Tmall marketplace.
 */
public class Category {
    /** Unique identifier for the category */
    private Integer id;

    /** Category name displayed to users, e.g., "Electronics" */
    private String name;

    /** List of products belonging to this category */
    private List<Product> products;

    /** Products organized into rows for display on category page */
    private List<List<Product>> productsByRow;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
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
}