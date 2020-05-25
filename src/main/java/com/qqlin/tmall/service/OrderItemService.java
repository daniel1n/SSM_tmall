package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.pojo.OrderItem;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
public interface OrderItemService {
    void add(OrderItem orderItem);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> orders);

    void fill(Order order);

    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);
}
