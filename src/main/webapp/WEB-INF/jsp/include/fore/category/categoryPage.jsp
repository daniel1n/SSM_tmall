<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 分类商品页面主体组件 --%>
<%-- 功能: 显示分类图片 + 排序栏 + 分类下商品列表 --%>
<%-- 依赖变量: c (分类对象，包含 products), param.sort (排序方式) --%>

<title>模仿天猫官网-${c.name}</title>
<div id="category">
    <div class="categoryPageDiv">
        <%-- 分类图片 --%>
        <img src="img/category/${c.id}.jpg">
        <%-- 排序栏 (综合/人气/新品/销量/价格) --%>
        <%@include file="sortBar.jsp" %>
        <%-- 分类下商品列表 --%>
        <%@include file="productsByCategory.jsp" %>
    </div>

</div>