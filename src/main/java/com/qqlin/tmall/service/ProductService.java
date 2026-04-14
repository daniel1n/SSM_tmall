package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.Product;

import java.util.List;

/**
 * Service interface for Product business operations.
 * Handles CRUD operations, product image management, and category associations.
 *
 * @author qqlin
 * @date 2020-5-20
 */
public interface ProductService {
    /**
     * Add a new product to the database.
     *
     * @param product Product entity to add (must have valid category via cid)
     */
    void add(Product product);

    /**
     * Delete a product by ID.
     * Cascades to delete associated product images and property values.
     *
     * @param id Product ID to delete
     */
    void delete(int id);

    /**
     * Update an existing product's information.
     *
     * @param product Product entity with updated values (ID must exist)
     */
    void update(Product product);

    /**
     * Get a product by ID with associated category and first product image populated.
     *
     * @param id Product ID
     * @return Product with category and firstProductImage set, or null if not found
     */
    Product get(int id);

    /**
     * Get all products for a specific category ordered by ID ascending.
     *
     * @param cid Category ID to filter products
     * @return List of products belonging to the specified category
     */
    List list(int cid);

    /**
     * Set the first product image for a product.
     * Traverses the product's image list and assigns the first single-type image.
     *
     * @param product Product to set first image for (images list must be loaded)
     */
    void setFirstProductImage(Product product);

    /**
     * Fill all categories with their products.
     * For each category, loads associated products and sets first product images.
     *
     * @param categories List of categories to populate with products
     */
    void fill(List<Category> categories);

    /**
     * Fill a single category with its products.
     * Loads products for the category and sets first product images.
     *
     * @param category Category to populate with products
     */
    void fill(Category category);

    /**
     * Fill multiple categories with products, grouped by row.
     * Each row contains up to 8 categories, with products ordered by ID descending.
     *
     * @param cs List of categories to populate with products
     */
    void fillByRow(List<Category> cs);

    /**
     * Set sale count and review count for a single product.
     *
     * @param product Product to set counts for
     */
    void setSaleAndReviewNumber(Product product);

    /**
     * Set sale count and review count for multiple products.
     *
     * @param products List of products to set counts for
     */
    void setSaleAndReviewNumber(List<Product> products);

    /**
     * Search products by keyword in product name.
     * Results are ordered by ID descending (newest first).
     *
     * @param keyword Search term for product name matching
     * @return List of matching products with images and categories populated
     */
    List<Product> search(String keyword);

    /**
     * Get multiple products by their IDs in a single query.
     * Optimized to avoid N+1 query problem when loading products for multiple items.
     *
     * @param ids List of product IDs to fetch
     * @return List of products matching the given IDs
     */
    List<Product> listByIds(List<Integer> ids);

    /**
     * Atomically decrease product stock.
     * Thread-safe operation that prevents overselling.
     *
     * @param pid Product ID
     * @param number Number of items to deduct
     * @return true if stock was successfully decreased, false if insufficient stock
     */
    boolean decreaseStock(int pid, int number);
}
