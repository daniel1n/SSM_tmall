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
 * Controller handling admin order management.
 * All endpoints are prefixed with /admin_order for admin backend management.
 *
 * @author qqlin
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

    /**
     * Display paginated list of all orders.
     *
     * URL: /admin_order_list
     * Method: GET
     * Parameters:
     *   - Page page: Pagination parameters (start, count) (required)
     * Returns: "admin/listOrder" (admin/listOrder.jsp)
     *
     * Business Logic:
     * 1. Use PageHelper for pagination
     * 2. Fetch all orders from database
     * 3. Calculate total count for pagination
     * 4. Fill order items for each order
     * 5. Return model with orders and page info
     */
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

    /**
     * Process order delivery (mark order as shipped).
     *
     * URL: /admin_order_delivery
     * Method: POST
     * Parameters:
     *   - Order order: Order object with id (required)
     * Returns: Redirect to "admin_order_list"
     *
     * Business Logic:
     * 1. Set delivery date to current date
     * 2. Update order status to WAIT_CONFIRM (waiting for buyer confirmation)
     * 3. Update order in database
     * 4. Redirect to order list
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(Order order) throws IOException {
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.WAIT_CONFIRM);
        orderService.update(order);
        return "redirect:admin_order_list";
    }

}
