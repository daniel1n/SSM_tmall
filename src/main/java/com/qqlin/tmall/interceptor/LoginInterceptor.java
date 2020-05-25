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
 * @author qqlin
 * @date 2020-5-22
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    public LoginInterceptor() {
        super();
    }

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false:
     * --- 从当前的拦截器往回执行所有拦截器的afterCompoletion(),再退出拦截器链
     * 如果返回true:
     * --- 执行下一个拦截器，知道所有拦截器都执行完毕
     * --- 再执行被拦截器的Controller
     * --- 然后进入拦截器链
     * --- 从最后一个拦截器往回执行所有的postHandle()
     * --- 接着再从最后一个拦截器往回执行所有的afterCompletion()
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
     * 在业务处理器处理请求执行完成后，生成视图之前执行的动作
     * 可以在modelAndView中加入数据，例如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
