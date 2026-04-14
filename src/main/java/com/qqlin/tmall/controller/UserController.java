package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqlin.tmall.pojo.User;
import com.qqlin.tmall.service.UserService;
import com.qqlin.tmall.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller handling admin user list display.
 * All endpoints are prefixed with /admin_user for admin backend management.
 *
 * @author qqlin
 */
@Controller
@RequestMapping("")
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Display paginated list of all users.
     *
     * URL: /admin_user_list
     * Method: GET
     * Parameters:
     *   - Page page: Pagination parameters (start, count) (required)
     * Returns: "admin/listUser" (admin/listUser.jsp)
     *
     * Business Logic:
     * 1. Use PageHelper for pagination
     * 2. Fetch all users from database
     * 3. Calculate total count for pagination
     * 4. Return model with users and page info
     */
    @RequestMapping("admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> userList = userService.list();

        int total = (int) new PageInfo<>(userList).getTotal();
        page.setTotal(total);

        model.addAttribute("us", userList);
        model.addAttribute("page", page);

        return "admin/listUser";
    }
}
