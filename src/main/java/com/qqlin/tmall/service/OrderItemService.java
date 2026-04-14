package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.pojo.OrderItem;

import java.util.List;

/**
 * Service interface for OrderItem business operations.
 * Handles individual order line item management and sales analytics.
 *
 * @author qqlin
 * @date 2020-5-21
 */
public interface OrderItemService {
    /**
     * Add a new order item to the database.
     *
     * @param orderItem OrderItem entity to add
     */
    void add(OrderItem orderItem);

    /**
     * Delete an order item by ID.
     *
     * @param id OrderItem ID to delete
     */
    void delete(int id);

    /**
     * Update an existing order item's information.
     *
     * @param orderItem OrderItem entity with updated values (ID must exist)
     */
    void update(OrderItem orderItem);

    /**
     * Get a single order item by ID.
     *
     * @param id OrderItem ID
     * @return OrderItem entity, or null if not found
     */
    OrderItem get(int id);

    /**
     * Get all order items ordered by ID ascending.
     *
     * @return List of all order items in the system
     */
    List<OrderItem> list();

    /**
     * Fill order items for a list of orders.
     * Loads associated order items for each order and calculates sale counts.
     *
     * @param orders List of orders to populate with order items
     */
    void fill(List<Order> orders);

    /**
     * Fill order items for a single order.
     * Loads associated order items and sets product first images.
     *
     * @param order Order to populate with order items
     */
    void fill(Order order);

    /**
     * Get total sale count for a specific product.
     * Sums all quantities from completed order items for the product.
     *
     * @param pid Product ID
     * @return Total number of units sold for the product
     */
    int getSaleCount(int pid);

    /**
     * Get all order items for a specific user.
     * Includes order items from non-deleted orders.
     *
     * @param uid User ID to filter order items
     * @return List of order items for the user
     */
    List<OrderItem> listByUser(int uid);
}
