package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.qqlin.comparator.*;
import com.qqlin.tmall.pojo.*;
import com.qqlin.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
@Controller
@RequestMapping("")
public class ForeController {
    final
    ReviewService reviewService;
    final
    CategoryService categoryService;
    final
    ProductService productService;
    final
    UserService userService;
    final
    ProductImageService productImageService;
    final
    PropertyValueService propertyValueService;
    final
    OrderService orderService;
    final
    OrderItemService orderItemService;

    public ForeController(CategoryService categoryService, ProductService productService, UserService userService, ProductImageService productImageService, PropertyValueService propertyValueService, OrderService orderService, OrderItemService orderItemService, ReviewService reviewService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.productImageService = productImageService;
        this.propertyValueService = propertyValueService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.reviewService = reviewService;
    }

    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> categorys = categoryService.list();
        productService.fill(categorys);
        productService.fillByRow(categorys);
        model.addAttribute("cs", categorys);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if (exist) {
            String m = "用户名已经被使用，不能使用";
            model.addAttribute("msg", m);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product(int pid, Model model) {
        Product product = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(
                product.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(
                product.getId(), ProductImageService.type_detail);

        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
        List<Review> reviews = reviewService.list(product.getId());

        productService.setSaleAndReviewNumber(product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", product);
        model.addAttribute("pvs", propertyValues);

        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());

        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(), new ProductAllComparator());
                    break;
                default:
            }
        }

        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        productService.setSaleAndReviewNumber(products);
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyOne(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        int orderItemId = 0;

        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().intValue() == product.getId().intValue()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                orderItemId = orderItem.getPid();
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItemService.add(orderItem);
            orderItemId = orderItem.getId();
        }

        return "redirect:forebuy?oiid=" + orderItemId;
    }

    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid, HttpSession session) {
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;

        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            ois.add(oi);
        }

        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model, HttpSession session) {
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        boolean found = false;

        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().intValue() == product.getId().intValue()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItemService.add(orderItem);
        }

        return "success";
    }

    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Model model, HttpSession session, int pid, int number) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }

        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().intValue() == pid) {
                orderItem.setNumber(number);
                orderItemService.update(orderItem);
                break;
            }
        }
        return "success";
    }

    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(Model model, HttpSession session, int oiid) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }
        orderItemService.delete(oiid);
        return "success";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(Model model, Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + RandomUtils.nextInt(10000);

        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.WAIT_PAY);
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");

        float total = orderService.add(order, orderItems);
        return "redirect:forealipay?oid=" + order.getId() + "&total=" + total;

    }

    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);

        return "fore/payed";
    }

    @RequestMapping("forebought")
    public String bought(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId(), OrderService.DELETE);

        orderItemService.fill(orders);

        model.addAttribute("os", orders);
        return "fore/bought";
    }

    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model, int oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        model.addAttribute("o", order);

        return "fore/confirmPay";
    }

    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAIT_REVIEW);
        order.setConfirmDate(new Date());
        orderService.update(order);

        return "fore/orderConfirmed";
    }

    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.DELETE);
        orderService.update(order);

        return "success";
    }

    @RequestMapping("forereview")
    public String review(Model model, int oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        Product product = order.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(product.getId());
        productService.setSaleAndReviewNumber(product);

        model.addAttribute("p", product);
        model.addAttribute("o", order);
        model.addAttribute("reviews", reviews);

        return "fore/review";
    }

    @RequestMapping("foredoreview")
    public String doreview(Model model,
                           HttpSession session,
                           @RequestParam("oid") int oid,
                           @RequestParam("pid") int pid,
                           String content) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.FINISH);
        orderService.update(order);

        Product product = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:forereview?oid=" + oid + "&showonly=true";
    }
}

