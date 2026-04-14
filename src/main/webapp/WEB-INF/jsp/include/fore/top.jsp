<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 前台页面顶部导航栏 --%>
<%-- 功能: 网站标志、用户登录状态、购物车数量显示 --%>
<%-- 依赖变量: user (当前登录用户), cartTotalItemNumber (购物车商品数量) --%>
<%-- 由 OtherInterceptor 拦截器在请求前填充 --%>

<nav class="top ">
    <%-- 天猫首页链接 --%>
    <a href="${contextPath}">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>

    <span>喵，欢迎来天猫</span>

    <%-- 已登录状态: 显示用户名 + 退出链接 --%>
    <c:if test="${!empty user}">
        <a href="loginPage">${user.name}</a>
        <a href="forelogout">退出</a>
    </c:if>

    <%-- 未登录状态: 显示登录/注册链接 --%>
    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>

    <%-- 右侧导航: 我的订单 + 购物车 --%>
    <span class="pull-right">
            <a href="forebought">我的订单</a>
            <a href="forecart">
            <%-- 购物车图标 + 商品数量 --%>
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            购物车<strong>${cartTotalItemNumber}</strong>件</a>
        </span>

</nav>