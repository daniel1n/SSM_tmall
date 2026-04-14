<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 分类下商品列表组件 --%>
<%-- 功能: 网格展示分类下所有商品，包含价格、销量、评价信息 --%>
<%-- 参数: categorycount (可选，控制显示商品数量) --%>
<%-- 依赖变量: c.products (商品列表) --%>

<%-- 默认显示100个商品 --%>
<c:if test="${empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="100"/>
</c:if>

<%-- 使用指定的显示数量 --%>
<c:if test="${!empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="${param.categorycount}"/>
</c:if>

<%-- 商品网格容器 --%>
<div class="categoryProducts">
    <c:forEach items="${c.products}" var="p" varStatus="stc">
        <c:if test="${stc.count<=categorycount}">
            <%-- 单个商品单元，price 属性用于价格筛选 --%>
            <div class="productUnit" price="${p.promotePrice}">
                <div class="productUnitFrame">
                    <%-- 商品主图 --%>
                    <a href="foreproduct?pid=${p.id}">
                        <img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
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
                    <div class="show1 productInfo">
                        <span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
                        <span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
                        <span class="wangwang">
					<a class="wangwanglink" href="#nowhere">
						<img src="img/site/wangwang.png">
					</a>

					</span>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
    <div style="clear:both"></div>
</div>