package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.qqlin.comparator.*;
import com.qqlin.tmall.pojo.*;
import com.qqlin.tmall.service.*;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Controller handling customer-facing e-commerce operations.
 * All endpoints are prefixed with /fore and serve end customers (not admins).
 *
 * @author qqlin
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

    /**
     * Display the homepage with all categories and featured products.
     *
     * URL: /forehome
     * Method: GET
     * Parameters: None
     * Returns: "fore/home" (fore/home.jsp)
     *
     * Business Logic:
     * 1. Fetch all categories from database
     * 2. Fill each category with its products
     * 3. Organize products into rows for homepage display
     * 4. Return model with categories for rendering
     */
    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> categorys = categoryService.list();
        productService.fill(categorys);
        productService.fillByRow(categorys);
        model.addAttribute("cs", categorys);
        return "fore/home";
    }

    /**
     * Process user registration.
     *
     * URL: /foreregister
     * Method: POST
     * Parameters:
     *   - User user: User object containing name, password and other registration info
     * Returns: "fore/register" on failure, "redirect:registerSuccessPage" on success
     *
     * Business Logic:
     * 1. Escape HTML characters in name to prevent XSS
     * 2. Check if username already exists
     * 3. If exists, return error message to registration page
     * 4. Otherwise, add user to database and redirect to success page
     */
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

    /**
     * Process user login with credentials.
     *
     * URL: /forelogin
     * Method: POST
     * Parameters:
     *   - String name: User login name (required)
     *   - String password: User password (required)
     * Returns: "fore/login" on failure, "redirect:forehome" on success
     *
     * Business Logic:
     * 1. Escape HTML characters in name to prevent XSS
     * 2. Query user by name and password
     * 3. If user not found, set error message and return login page
     * 4. Otherwise, store user in session and redirect to homepage
     */
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

    /**
     * Log out the current user by removing user from session.
     *
     * URL: /forelogout
     * Method: GET
     * Parameters: None
     * Returns: "redirect:forehome" (redirects to homepage)
     *
     * Business Logic:
     * 1. Remove "user" attribute from session
     * 2. Redirect to homepage
     */
    @RequestMapping("forelogout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:forehome";
    }

    /**
     * Display product detail page.
     *
     * URL: /foreproduct
     * Method: GET
     * Parameters:
     *   - int pid: Product ID (required)
     * Returns: "fore/product" (fore/product.jsp)
     *
     * Business Logic:
     * 1. Fetch product by ID
     * 2. Load single images and detail images for the product
     * 3. Load all property values for the product
     * 4. Load all reviews for the product
     * 5. Calculate sale count and review count for the product
     * 6. Return model with product, property values, and reviews
     */
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

    /**
     * Check if user is logged in (AJAX endpoint).
     *
     * URL: /forecheckLogin
     * Method: POST
     * Parameters: None
     * Returns: "success" if logged in, "fail" otherwise
     *
     * Business Logic:
     * 1. Check if "user" attribute exists in session
     * 2. Return appropriate response
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return "success";
        }
        return "fail";
    }

    /**
     * Process user login via AJAX (JSON response).
     *
     * URL: /foreloginAjax
     * Method: POST
     * Parameters:
     *   - String name: User login name (required)
     *   - String password: User password (required)
     * Returns: "success" on successful login, "fail" otherwise
     *
     * Business Logic:
     * 1. Escape HTML characters in name to prevent XSS
     * 2. Query user by name and password
     * 3. If user found, store in session and return "success"
     * 4. Otherwise return "fail"
     */
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

    /**
     * Display products in a category with optional sorting.
     *
     * URL: /forecategory
     * Method: GET
     * Parameters:
     *   - int cid: Category ID (required)
     *   - String sort: Sort type - "review", "date", "saleCount", "price", "all" (optional)
     * Returns: "fore/category" (fore/category.jsp)
     *
     * Business Logic:
     * 1. Fetch category by ID
     * 2. Fill category with its products
     * 3. Set sale and review numbers for each product
     * 4. Sort products by the specified sort type if provided
     * 5. Return model with category for rendering
     */
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

    /**
     * Search products by keyword.
     *
     * URL: /foresearch
     * Method: GET
     * Parameters:
     *   - String keyword: Search keyword (required)
     * Returns: "fore/searchResult" (fore/searchResult.jsp)
     *
     * Business Logic:
     * 1. Search products matching the keyword (limit 20 results)
     * 2. Set sale and review numbers for each product
     * 3. Return model with search results
     */
    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        productService.setSaleAndReviewNumber(products);
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

    /**
     * Add product to cart or update quantity if already in cart.
     * Also called when buying directly (without cart).
     *
     * URL: /forebuyone
     * Method: GET
     * Parameters:
     *   - int pid: Product ID (required)
     *   - int num: Quantity to add (required)
     * Returns: Redirect to "forebuy" with order item ID
     *
     * Business Logic:
     * 1. Get current user from session
     * 2. Check if product already exists in user's order items
     * 3. If exists, update quantity by adding num
     * 4. If not, create new order item
     * 5. Redirect to checkout page with order item ID
     */
    @RequestMapping("forebuyone")
    public String buyOne(int pid, int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int orderItemId = addOrUpdateCartItem(user.getId(), pid, num);
        return "redirect:forebuy?oiid=" + orderItemId;
    }

    /**
     * Display checkout page with order items to purchase.
     *
     * URL: /forebuy
     * Method: GET
     * Parameters:
     *   - String[] oiid: Array of order item IDs to purchase (required)
     * Returns: "fore/buy" (fore/buy.jsp)
     *
     * Business Logic:
     * 1. Convert string IDs to integers
     * 2. Fetch each order item and calculate subtotal
     * 3. Calculate total price
     * 4. Store order items in session
     * 5. Return model with total for rendering
     */
    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid, HttpSession session) {
        List<OrderItem> ois = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            BigDecimal itemTotal = oi.getProduct().getPromotePrice()
                    .multiply(BigDecimal.valueOf(oi.getNumber()));
            total = total.add(itemTotal);
            ois.add(oi);
        }

        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    /**
     * Add product to shopping cart (AJAX endpoint).
     *
     * URL: /foreaddCart
     * Method: POST
     * Parameters:
     *   - int pid: Product ID (required)
     *   - int num: Quantity to add (required)
     * Returns: "success" always
     *
     * Business Logic:
     * 1. Get current user from session
     * 2. Check if product already exists in user's order items (cart)
     * 3. If exists, update quantity by adding num
     * 4. If not, create new order item
     */
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        addOrUpdateCartItem(user.getId(), pid, num);
        return "success";
    }

    /**
     * Display shopping cart page.
     *
     * URL: /forecart
     * Method: GET
     * Parameters: None
     * Returns: "fore/cart" (fore/cart.jsp)
     *
     * Business Logic:
     * 1. Get current user from session
     * 2. Fetch all order items (cart items) for the user
     * 3. Return model with order items for rendering
     */
    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    /**
     * Update the quantity of an order item in cart (AJAX endpoint).
     *
     * URL: /forechangeOrderItem
     * Method: POST
     * Parameters:
     *   - int pid: Product ID (required)
     *   - int number: New quantity (required)
     * Returns: "success" on success, "fail" if user not logged in
     *
     * Business Logic:
     * 1. Check if user is logged in
     * 2. Find order item with matching product ID
     * 3. Update quantity to the new value
     */
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

    /**
     * Remove an order item from cart (AJAX endpoint).
     *
     * URL: /foredeleteOrderItem
     * Method: POST
     * Parameters:
     *   - int oiid: Order item ID to delete (required)
     * Returns: "success" on success, "fail" if user not logged in
     *
     * Business Logic:
     * 1. Check if user is logged in
     * 2. Delete the order item by ID
     */
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

    /**
     * Create a new order from checkout.
     *
     * URL: /forecreateOrder
     * Method: POST
     * Parameters:
     *   - Order order: Order object with shipping address and other details
     * Returns: Redirect to payment page with order ID and total
     *
     * Business Logic:
     * 1. Get current user from session
     * 2. Generate unique order code (timestamp + random)
     * 3. Set order creation date, user ID, and initial status (wait pay)
     * 4. Get order items from session
     * 5. Create order and associated order items in database
     * 6. Redirect to payment page
     */
    @RequestMapping("forecreateOrder")
    public String createOrder(Model model, Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + RandomUtils.nextInt(0, 10000);

        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.WAIT_PAY);
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");

        BigDecimal total = orderService.add(order, orderItems);
        return "redirect:forealipay?oid=" + order.getId() + "&total=" + total;

    }

    /**
     * Handle successful payment callback and display confirmation.
     *
     * URL: /forepayed
     * Method: GET
     * Parameters:
     *   - int oid: Order ID (required)
     *   - float total: Total amount paid (required)
     * Returns: "fore/payed" (fore/payed.jsp)
     *
     * Business Logic:
     * 1. Fetch order by ID
     * 2. Update order status to WAIT_DELIVERY
     * 3. Set payment date to current date
     * 4. Update order in database
     * 5. Return model with order for rendering
     */
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);

        return "fore/payed";
    }

    /**
     * Display user's order history (bought items).
     *
     * URL: /forebought
     * Method: GET
     * Parameters: None
     * Returns: "fore/bought" (fore/bought.jsp)
     *
     * Business Logic:
     * 1. Get current user from session
     * 2. Fetch all orders for the user (excluding deleted)
     * 3. Fill order items for each order
     * 4. Return model with orders for rendering
     */
    @RequestMapping("forebought")
    public String bought(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId(), OrderService.DELETE);

        orderItemService.fill(orders);

        model.addAttribute("os", orders);
        return "fore/bought";
    }

    /**
     * Display order confirmation page before confirming receipt.
     *
     * URL: /foreconfirmPay
     * Method: GET
     * Parameters:
     *   - int oid: Order ID (required)
     * Returns: "fore/confirmPay" (fore/confirmPay.jsp)
     *
     * Business Logic:
     * 1. Fetch order by ID
     * 2. Fill order items for the order
     * 3. Return model with order for rendering
     */
    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model, int oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        model.addAttribute("o", order);

        return "fore/confirmPay";
    }

    /**
     * Confirm order receipt and complete the order.
     *
     * URL: /foreorderConfirmed
     * Method: GET
     * Parameters:
     *   - int oid: Order ID (required)
     * Returns: "fore/orderConfirmed" (fore/orderConfirmed.jsp)
     *
     * Business Logic:
     * 1. Fetch order by ID
     * 2. Update order status to WAIT_REVIEW (waiting for review)
     * 3. Set confirmation date to current date
     * 4. Update order in database
     */
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAIT_REVIEW);
        order.setConfirmDate(new Date());
        orderService.update(order);

        return "fore/orderConfirmed";
    }

    /**
     * Delete (cancel) an order (AJAX endpoint).
     *
     * URL: /foredeleteOrder
     * Method: POST
     * Parameters:
     *   - int oid: Order ID to delete (required)
     * Returns: "success"
     *
     * Business Logic:
     * 1. Fetch order by ID
     * 2. Set order status to DELETE (logical deletion)
     * 3. Update order in database
     */
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.DELETE);
        orderService.update(order);

        return "success";
    }

    /**
     * Display product review page for a completed order.
     *
     * URL: /forereview
     * Method: GET
     * Parameters:
     *   - int oid: Order ID (required)
     * Returns: "fore/review" (fore/review.jsp)
     *
     * Business Logic:
     * 1. Fetch order by ID
     * 2. Fill order items for the order
     * 3. Get the first product from order items
     * 4. Fetch all reviews for the product
     * 5. Calculate sale and review numbers for the product
     * 6. Return model with order, product, and reviews
     */
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

    /**
     * Submit a product review for a completed order.
     *
     * URL: /foredoreview
     * Method: POST
     * Parameters:
     *   - int oid: Order ID (required)
     *   - int pid: Product ID (required)
     *   - String content: Review content (required)
     * Returns: Redirect to "forereview" with order ID and showonly flag
     *
     * Business Logic:
     * 1. Fetch order by ID and update status to FINISH
     * 2. Escape HTML in review content to prevent XSS
     * 3. Get current user from session
     * 4. Create new review with content, product ID, user ID, and date
     * 5. Add review to database
     * 6. Redirect to review page
     */
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

    /**
     * Helper method to add or update a cart item.
     * Checks if the product already exists in user's cart, updates quantity if so, otherwise creates new item.
     *
     * @param uid User ID
     * @param pid Product ID
     * @param num Quantity to add
     * @return OrderItem ID
     */
    private int addOrUpdateCartItem(int uid, int pid, int num) {
        List<OrderItem> orderItems = orderItemService.listByUser(uid);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().intValue() == pid) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                return orderItem.getId();
            }
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setUid(uid);
        orderItem.setNumber(num);
        orderItem.setPid(pid);
        orderItemService.add(orderItem);
        return orderItem.getId();
    }
}

