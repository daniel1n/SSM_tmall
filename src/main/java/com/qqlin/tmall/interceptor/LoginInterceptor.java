package com.qqlin.tmall.interceptor;

import com.qqlin.tmall.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

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
 * <p>Spring Boot 适配说明:</p>
 * <ul>
 *   <li>使用 HandlerInterceptor 替代已废弃的 HandlerInterceptorAdapter</li>
 *   <li>移除了 @Autowired 依赖注入，改为通过构造器或参数传递</li>
 *   <li>通过 new LoginInterceptor() 在 WebMvcConfig 中创建实例</li>
 * </ul>
 *
 * @author qqlin
 * @date 2020-5-22
 * @see HandlerInterceptor
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 无参构造器 - Spring Boot 通过 new 创建实例时使用
     */
    public LoginInterceptor() {
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
}
