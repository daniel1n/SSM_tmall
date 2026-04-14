<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 商品详情区域组件 --%>
<%-- 功能: 显示产品参数、商品详情图片 --%>
<%-- 与 productReview.jsp 互斥显示，点击导航项切换 --%>
<%-- 依赖变量: p (商品对象，包含 productDetailImages), pvs (属性值列表) --%>

<div class="productDetailDiv">
    <%-- 详情/评价 切换标签 --%>
    <div class="productDetailTopPart">
        <a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
        <a href="#nowhere" class="productDetailTopReviewLink">累计评价 <span
                class="productDetailTopReviewLinkNumber">${p.reviewCount}</span> </a>
    </div>

    <%-- 产品参数区域 --%>
    <div class="productParamterPart">
        <div class="productParamter">产品参数：</div>

        <%-- 参数列表 (截取前10字符显示) --%>
        <div class="productParamterList">
            <c:forEach items="${pvs}" var="pv">
                <span>${pv.property.name}:  ${fn:substring(pv.value, 0, 10)} </span>
            </c:forEach>
        </div>
        <div style="clear:both"></div>
    </div>

    <%-- 商品详情图片区域 --%>
    <div class="productDetailImagesPart">
        <c:forEach items="${p.productDetailImages}" var="pi">
            <img src="img/productDetail/${pi.id}.jpg">
        </c:forEach>
    </div>
</div>