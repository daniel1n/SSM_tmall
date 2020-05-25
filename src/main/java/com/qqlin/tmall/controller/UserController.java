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
 * @author qqlin
 * @date 2020-5-21
 */
@Controller
@RequestMapping("")
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
