package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.OrderMapper;
import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.pojo.OrderExample;
import com.qqlin.tmall.pojo.OrderItem;
import com.qqlin.tmall.pojo.User;
import com.qqlin.tmall.service.OrderItemService;
import com.qqlin.tmall.service.OrderService;
import com.qqlin.tmall.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
@Service
public class OrderServiceImpl implements OrderService {

    final
    OrderItemService orderItemService;

    final
    OrderMapper orderMapper;

    final
    UserService userService;

    public OrderServiceImpl(OrderMapper orderMapper, UserService userService, OrderItemService orderItemService) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.orderItemService = orderItemService;
    }


    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public BigDecimal add(Order order, List<OrderItem> orderItems) {
        BigDecimal total = BigDecimal.ZERO;
        add(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            BigDecimal itemTotal = orderItem.getProduct().getPromotePrice()
                    .multiply(BigDecimal.valueOf(orderItem.getNumber()));
            total = total.add(itemTotal);
        }
        return total;
    }

    @Override
    public List list(int uid, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    private void setUser(List<Order> result) {
        for (Order order : result) {
            setUser(order);
        }
    }

    private void setUser(Order order) {
        int uid = order.getUid();
        User user = userService.get(uid);
        order.setUser(user);
    }


}
