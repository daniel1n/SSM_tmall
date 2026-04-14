package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Category;

import java.util.List;

/**
 * Service interface for Category business operations.
 * Handles CRUD operations for product categories and their hierarchical structure.
 *
 * @author qqlin
 * @date 2020-5-18
 */
public interface CategoryService {
    /**
     * Get all categories ordered by ID ascending.
     *
     * @return List of all categories in the system
     */
    List<Category> list();

    /**
     * Add a new category to the database.
     *
     * @param category Category entity to add (name must be non-empty)
     */
    void add(Category category);

    /**
     * Delete a category by ID.
     * Note: Will fail if category is associated with existing products.
     *
     * @param id Category ID to delete
     */
    void delete(int id);

    /**
     * Get a single category by ID.
     *
     * @param id Category ID
     * @return Category entity, or null if not found
     */
    Category get(int id);

    /**
     * Update an existing category's information.
     *
     * @param category Category entity with updated values (ID must exist)
     */
    void update(Category category);
}
