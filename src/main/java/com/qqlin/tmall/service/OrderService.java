package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.pojo.OrderItem;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
public interface OrderService {

    String WAIT_PAY = "waitPay";
    String WAIT_DELIVERY = "waitDelivery";
    String WAIT_CONFIRM = "waitConfirm";
    String WAIT_REVIEW = "waitReview";
    String FINISH = "finish";
    String DELETE = "delete";

    void add(Order order);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    List list();

    float add(Order order, List<OrderItem> orderItems);

    List list(int uid, String excludedStatus);
}
