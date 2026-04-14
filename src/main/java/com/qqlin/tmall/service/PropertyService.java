package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Property;

import java.util.List;

/**
 * Service interface for Property business operations.
 * Handles CRUD operations for product properties (specifications) within categories.
 *
 * @author qqlin
 * @date 2020-5-20
 */
public interface PropertyService {
    /**
     * Add a new property to a category.
     *
     * @param property Property entity to add (must have valid category via cid)
     */
    void add(Property property);

    /**
     * Delete a property by ID.
     * Note: Cascades to delete associated property values.
     *
     * @param id Property ID to delete
     */
    void delete(int id);

    /**
     * Update an existing property's information.
     *
     * @param property Property entity with updated values (ID must exist)
     */
    void update(Property property);

    /**
     * Get a single property by ID.
     *
     * @param id Property ID
     * @return Property entity, or null if not found
     */
    Property get(int id);

    /**
     * Get all properties for a specific category ordered by ID ascending.
     *
     * @param cid Category ID to filter properties
     * @return List of properties belonging to the specified category
     */
    List list(int cid);
}
