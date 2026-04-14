<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 搜索结果商品列表组件 --%>
<%-- 功能: 网格展示搜索匹配的商品，包含价格、销量、评价信息 --%>
<%-- 依赖变量: ps (商品列表) --%>

<div class="searchProducts">

    <c:forEach items="${ps}" var="p">
    <%-- 单个商品单元，price 属性用于价格筛选 --%>
    <div class="productUnit" price="${p.promotePrice}">
        <%-- 商品主图 --%>
        <a href="foreproduct?pid=${p.id}">
            <img class="productImage" src="img/productSingle/${p.firstProductImage.id}.jpg">
        </a>
        <%-- 促销价 --%>
        <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}"
                                                      minFractionDigits="2"/></span>
        <%-- 商品名称 (截取前50字符) --%>
        <a class="productLink" href="foreproduct?pid=${p.id}">
                ${fn:substring(p.name, 0, 50)}
        </a>

        <%-- 天猫专卖链接 --%>
        <a class="tmallLink" href="foreproduct?pid=${p.id}">天猫专卖</a>

        <%-- 商品统计信息: 月成交量、评价数 --%>
        <div class="productInfo">
            <span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
            <span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
            <span class="wangwang"><img src="img/site/wangwang.png"></span>
        </div>

    </div>
    </c:forEach>
    <%-- 无搜索结果提示 --%>
    <c:if test="${empty ps}">
    <div class="noMatch">没有满足条件的产品
        <div>
            </c:if>

            <div style="clear:both"></div>
        </div>