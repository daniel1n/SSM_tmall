package com.qqlin.tmall.interceptor;

import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.OrderItem;
import com.qqlin.tmall.pojo.User;
import com.qqlin.tmall.service.CategoryService;
import com.qqlin.tmall.service.OrderItemService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Navigation data preparation interceptor for enriching the model with common data.
 *
 * <p>This interceptor prepares data needed for the navigation bar and other
 * shared UI elements across all pages. It runs after the controller executes
 * but before the view is rendered.</p>
 *
 * <p>Data prepared by this interceptor includes:</p>
 * <ul>
 *   <li>Category list (cs) - For the category dropdown/search in navigation</li>
 *   <li>Context path (contextPath) - For proper URL construction and logo links</li>
 *   <li>Cart total item number (cartTotalItemNumber) - Number of items in user's cart</li>
 * </ul>
 *
 * <p>Spring Boot 适配说明:</p>
 * <ul>
 *   <li>使用 HandlerInterceptor 替代已废弃的 HandlerInterceptorAdapter</li>
 *   <li>移除了 @Autowired 依赖注入</li>
 *   <li>通过构造器接收服务实例</li>
 *   <li>在 WebMvcConfig 中创建实例并注入依赖</li>
 * </ul>
 *
 * @author qqlin
 * @date 2020-5-22
 * @see HandlerInterceptor
 */
public class OtherInterceptor implements HandlerInterceptor {

    /** 分类服务 - 用于获取分类列表 */
    private final CategoryService categoryService;

    /** 订单项服务 - 用于获取购物车数量 */
    private final OrderItemService orderItemService;

    /**
     * 构造器 - 接收服务依赖注入
     *
     * @param categoryService  分类服务
     * @param orderItemService 订单项服务
     */
    public OtherInterceptor(CategoryService categoryService, OrderItemService orderItemService) {
        this.categoryService = categoryService;
        this.orderItemService = orderItemService;
    }

    /**
     * Called before the handler is executed.
     *
     * <p>This implementation always returns {@code true} to allow the request
     * to proceed. The authentication check is handled by LoginInterceptor.</p>
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param handler  the handler being intercepted
     * @return always {@code true}
     * @throws Exception if an error occurs
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /**
     * Called after the handler executes but before the view is rendered.
     *
     * <p>This method populates the session with data required for navigation:</p>
     * <ul>
     *   <li>Category list ("cs") - For display in the search bar dropdown</li>
     *   <li>Context path ("contextPath") - For proper URL construction and logo links</li>
     *   <li>Cart total item number ("cartTotalItemNumber") - Total items in user's shopping cart</li>
     * </ul>
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param handler     the handler that was executed
     * @param modelAndView the ModelAndView object (not used in this implementation)
     * @throws Exception if an error occurs while preparing navigation data
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Retrieves the category list and stores it in session for the search bar dropdown
        List<Category> categories = categoryService.list();
        request.getSession().setAttribute("cs", categories);

        // Retrieves the context path (e.g., "tmall_ssm") for proper URL construction.
        // This ensures the logo link redirects to the home page correctly.
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        request.getSession().setAttribute("contextPath", contextPath);

        // Calculates the total number of items in the user's shopping cart
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if (null != user) {
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            for (OrderItem orderItem : orderItems) {
                cartTotalItemNumber += orderItem.getNumber();
            }
        }
        request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }
}
