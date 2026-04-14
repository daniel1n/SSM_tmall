package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.OrderItemMapper;
import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.pojo.OrderItem;
import com.qqlin.tmall.pojo.OrderItemExample;
import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.service.OrderItemService;
import com.qqlin.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem result = orderItemMapper.selectByPrimaryKey(id);
        setProduct(result);
        return result;
    }

    private void setProduct(OrderItem result) {
        Product product = productService.get(result.getPid());
        result.setProduct(product);
    }

    /**
     * Batch set products for multiple order items using optimized query.
     * Fixes N+1 query problem by fetching all products in a single database call.
     *
     * @param orderItems List of order items to populate with products
     */
    private void setProduct(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return;
        }
        // Extract all product IDs
        List<Integer> pids = orderItems.stream()
                .map(OrderItem::getPid)
                .collect(java.util.stream.Collectors.toList());

        // Batch fetch all products in ONE query
        List<Product> products = productService.listByIds(pids);

        // Create a map for quick lookup: pid -> Product
        java.util.Map<Integer, Product> productMap = products.stream()
                .collect(java.util.stream.Collectors.toMap(Product::getId, p -> p));

        // Associate products with order items
        for (OrderItem orderItem : orderItems) {
            orderItem.setProduct(productMap.get(orderItem.getPid()));
        }
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);

        BigDecimal total = BigDecimal.ZERO;
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            BigDecimal itemTotal = orderItem.getProduct().getPromotePrice()
                    .multiply(BigDecimal.valueOf(orderItem.getNumber()));
            total = total.add(itemTotal);
            totalNumber += orderItem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        return orderItems.stream().mapToInt(OrderItem::getNumber).sum();
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> result = orderItemMapper.selectByExample(example);
        setProduct(result);
        return result;
    }
}

