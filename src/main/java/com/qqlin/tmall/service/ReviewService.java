package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Review;

import java.util.List;

/**
 * Service interface for Review business operations.
 * Handles product review management and review count analytics.
 *
 * @author qqlin
 * @date 2020-5-21
 */
public interface ReviewService {
    /**
     * Add a new review to the database.
     *
     * @param review Review entity to add (must have valid product via pid)
     */
    void add(Review review);

    /**
     * Delete a review by ID.
     *
     * @param id Review ID to delete
     */
    void delete(int id);

    /**
     * Update an existing review's content.
     *
     * @param review Review entity with updated values (ID must exist)
     */
    void update(Review review);

    /**
     * Get a single review by ID.
     *
     * @param id Review ID
     * @return Review entity, or null if not found
     */
    Review get(int id);

    /**
     * Get all reviews for a specific product ordered by date descending (newest first).
     *
     * @param pid Product ID to filter reviews
     * @return List of reviews for the specified product
     */
    List list(int pid);

    /**
     * Get the total number of reviews for a specific product.
     *
     * @param pid Product ID
     * @return Total count of reviews for the product
     */
    int getCount(int pid);
}
