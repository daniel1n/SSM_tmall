package com.qqlin.tmall.interceptor;

import com.qqlin.tmall.pojo.User;
import com.qqlin.tmall.service.CategoryService;
import com.qqlin.tmall.service.OrderItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Authentication guard interceptor for protecting user-only endpoints.
 *
 * This interceptor validates user login status for requests starting with "/fore".
 * It checks if the user is logged in by looking for a "user" attribute in the session.
 * If the user is not logged in and the requested endpoint requires authentication,
 * the request is redirected to the login page.
 *
 * <p>Endpoints that do not require authentication: home, checkLogin, register,
 * loginAjax, login, product, category, search</p>
 *
 * <p>This interceptor extends HandlerInterceptorAdapter and overrides:</p>
 * <ul>
 *   <li>preHandle() - Validates authentication before controller execution</li>
 *   <li>postHandle() - Called after controller processing (empty implementation)</li>
 *   <li>afterCompletion() - Called after complete request processing (empty implementation)</li>
 * </ul>
 *
 * @author qqlin
 * @date 2020-5-22
 * @see HandlerInterceptorAdapter
 * @see javax.servlet.http.HttpSession
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /** Service for retrieving category information (injected by Spring) */
    @Autowired
    CategoryService categoryService;

    /** Service for retrieving order item information (injected by Spring) */
    @Autowired
    OrderItemService orderItemService;

    /**
     * Default constructor for LoginInterceptor.
     * Delegates to parent constructor.
     */
    public LoginInterceptor() {
        super();
    }

    /**
     * Validates user authentication before allowing access to protected endpoints.
     *
     * <p>This method is called before the target controller handler is executed.
     * For requests starting with "/fore", it checks if the requested method requires
     * authentication. If authentication is required and no user is logged in,
     * the request is redirected to "loginPage".</p>
     *
     * @param request  the HTTP request containing URI and session information
     * @param response the HTTP response for redirecting unauthenticated users
     * @param handler  the handler being intercepted (typically the controller method)
     * @return {@code true} if the request should proceed to the controller,
     *         {@code false} if the request should be blocked (redirected to login)
     * @throws Exception if an error occurs during authentication validation
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"
        };

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);

        if (uri.startsWith("/fore")) {
            String method = StringUtils.substringAfterLast(uri, "/fore");
            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
                User user = (User) session.getAttribute("user");
                if (null == user) {
                    response.sendRedirect("loginPage");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Called after the handler executes but before the view is rendered.
     *
     * <p>This method is called after the controller handler completes successfully
     * but before the view is rendered. Currently, this is an empty implementation
     * that can be extended to add common model attributes or modify the ModelAndView.</p>
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param handler     the handler that was executed
     * @param modelAndView the ModelAndView object containing model data and view name
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * Called after the request is completely processed and the view has been rendered.
     *
     * <p>This callback is invoked after the entire request cycle completes, including
     * view rendering. It is typically used for cleanup operations such as releasing
     * resources. When an exception is thrown in any interceptor, this method is
     * called from the current interceptor back through the chain.</p>
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param handler  the handler that was executed
     * @param ex       any exception that was thrown during request processing,
     *                 or {@code null} if no exception occurred
     * @throws Exception if an error occurs during cleanup
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * Called when a request is handled concurrently by a servlet's asynchronous processing.
     *
     * <p>This method is invoked when the request is processed asynchronously, allowing
     * the interceptor to handle async dispatch scenarios. Currently, this is an
     * empty implementation that delegates to the parent class.</p>
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param handler  the handler for async processing
     * @throws Exception if an error occurs during async handling
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
