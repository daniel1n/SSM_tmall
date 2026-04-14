package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.pojo.OrderItem;

import java.util.List;

/**
 * Service interface for Order business operations.
 * Handles order lifecycle management including creation, status updates, and user order queries.
 *
 * @author qqlin
 * @date 2020-5-21
 */
public interface OrderService {
    /**
     * Status constant for orders awaiting payment.
     */
    String WAIT_PAY = "waitPay";

    /**
     * Status constant for orders awaiting delivery (paid, preparing shipment).
     */
    String WAIT_DELIVERY = "waitDelivery";

    /**
     * Status constant for orders awaiting buyer confirmation of receipt.
     */
    String WAIT_CONFIRM = "waitConfirm";

    /**
     * Status constant for orders awaiting buyer review submission.
     */
    String WAIT_REVIEW = "waitReview";

    /**
     * Status constant for completed/finalized orders.
     */
    String FINISH = "finish";

    /**
     * Status constant for deleted/cancelled orders.
     */
    String DELETE = "delete";

    /**
     * Add a new order to the database.
     *
     * @param order Order entity to add
     */
    void add(Order order);

    /**
     * Delete an order by ID.
     *
     * @param id Order ID to delete
     */
    void delete(int id);

    /**
     * Update an existing order's information.
     *
     * @param order Order entity with updated values (ID must exist)
     */
    void update(Order order);

    /**
     * Get a single order by ID.
     *
     * @param id Order ID
     * @return Order entity, or null if not found
     */
    Order get(int id);

    /**
     * Get all orders ordered by order date descending (newest first).
     *
     * @return List of all orders in the system
     */
    List list();

    /**
     * Add a new order with associated order items.
     * Deducts stock from products and creates order item records.
     *
     * @param order      Order entity to add
     * @param orderItems List of order items associated with this order
     * @return Total order amount
     */
    float add(Order order, List<OrderItem> orderItems);

    /**
     * Get all orders for a specific user, excluding orders with a given status.
     * Used to display user order history.
     *
     * @param uid              User ID to filter orders
     * @param excludedStatus   Status to exclude (e.g., DELETE to exclude cancelled orders)
     * @return List of orders for the user
     */
    List list(int uid, String excludedStatus);
}
