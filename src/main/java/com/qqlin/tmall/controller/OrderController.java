package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqlin.tmall.pojo.Order;
import com.qqlin.tmall.service.OrderItemService;
import com.qqlin.tmall.service.OrderService;
import com.qqlin.tmall.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
@Controller
@RequestMapping("")
public class OrderController {
    final
    OrderService orderService;

    final
    OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> orderList = orderService.list();

        int total = (int) new PageInfo<>(orderList).getTotal();
        page.setTotal(total);

        orderItemService.fill(orderList);

        model.addAttribute("os", orderList);
        model.addAttribute("page", page);

        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order order) throws IOException {
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.WAIT_CONFIRM);
        orderService.update(order);
        return "redirect:admin_order_list";
    }

}
