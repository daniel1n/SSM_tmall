package com.qqlin.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller handling simple page redirects for the frontend.
 * Provides navigation to static pages without business logic.
 *
 * @author qqlin
 */
@Controller
@RequestMapping("")
public class PageController {
    /**
     * Redirect to user registration page.
     *
     * URL: /registerPage
     * Method: GET
     * Parameters: None
     * Returns: "fore/register" (fore/register.jsp)
     */
    @RequestMapping("registerPage")
    public String registerPage() {
        return "fore/register";
    }

    /**
     * Redirect to registration success page.
     *
     * URL: /registerSuccessPage
     * Method: GET
     * Parameters: None
     * Returns: "fore/registerSuccess" (fore/registerSuccess.jsp)
     */
    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }

    /**
     * Redirect to login page.
     *
     * URL: /loginPage
     * Method: GET
     * Parameters: None
     * Returns: "fore/login" (fore/login.jsp)
     */
    @RequestMapping("loginPage")
    public String loginPage() {
        return "fore/login";
    }

    /**
     * Redirect to Alipay payment page.
     *
     * URL: /forealipay
     * Method: GET
     * Parameters: None
     * Returns: "fore/alipay" (fore/alipay.jsp)
     */
    @RequestMapping("forealipay")
    public String alipay() {
        return "fore/alipay";
    }
}
