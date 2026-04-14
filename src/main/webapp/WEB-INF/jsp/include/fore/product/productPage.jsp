<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 商品详情页主体组件 --%>
<%-- 功能: 分类图片 + 商品信息/图片组件 + 商品详情 + 商品评价 --%>
<%-- 依赖变量: p (商品对象), pvs (属性值列表), reviews (评价列表) --%>

<title>模仿天猫官网 ${p.name}</title>

<%-- 分类图片 --%>
<div class="categoryPictureInProductPageDiv">
    <img class="categoryPictureInProductPage" src="img/category/${p.category.id}.jpg">
</div>

<%-- 商品页面主体容器 --%>
<div class="productPageDiv">

    <%-- 商品图片与购买信息组件 --%>
    <%@include file="imgAndInfo.jsp" %>

    <%-- 商品评价组件 (初始隐藏，点击切换显示) --%>
    <%@include file="productReview.jsp" %>

    <%-- 商品详情组件 (初始显示) --%>
    <%@include file="productDetail.jsp" %>
</div>