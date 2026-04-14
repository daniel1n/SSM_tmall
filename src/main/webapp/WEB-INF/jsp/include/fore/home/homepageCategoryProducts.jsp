<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 首页分类商品展示区域 --%>
<%-- 功能: 按分类展示商品，每个分类显示最多5个商品 --%>
<%-- 参数: categorycount (可选，默认为100，控制显示的分类数量) --%>
<%-- 依赖变量: cs (categories 列表，每个category包含products列表) --%>

<%-- 如果未指定 categorycount 参数，默认显示100个分类 --%>
<c:if test="${empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="100"/>
</c:if>

<%-- 如果指定了 categorycount 参数，使用指定值 --%>
<c:if test="${!empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="${param.categorycount}"/>
</c:if>

<%-- 首页分类商品容器 --%>
<div class="homepageCategoryProducts">
    <c:forEach items="${cs}" var="c" varStatus="stc">
        <%-- 仅显示前 categorycount 个分类 --%>
        <c:if test="${stc.count<=categorycount}">
            <div class="eachHomepageCategoryProducts">
                <%-- 左侧标记线 + 分类名称 --%>
                <div class="left-mark"></div>
                <span class="categoryTitle">${c.name}</span>
                <br>
                <%-- 该分类下的商品列表，最多显示5个 --%>
                <c:forEach items="${c.products}" var="p" varStatus="st">
                    <c:if test="${st.count<=5}">
                        <div class="productItem">
                            <%-- 商品主图 (中等尺寸) --%>
                            <a href="foreproduct?pid=${p.id}"><img width="100px"
                                                                   src="img/productSingle_middle/${p.firstProductImage.id}.jpg"></a>
                            <%-- 商品名称 (截取前20字符) + 促销标记 --%>
                            <a class="productItemDescLink" href="foreproduct?pid=${p.id}">
                                <span class="productItemDesc">[热销]
                                ${fn:substring(p.name, 0, 20)}
                                </span>
                            </a>
                            <%-- 商品促销价 --%>
                            <span class="productPrice">
                                <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                            </span>
                        </div>
                    </c:if>
                </c:forEach>
                <div style="clear:both"></div>
            </div>
        </c:if>
    </c:forEach>

    <%-- 页面底部结束标记图 --%>
    <img id="endpng" class="endpng" src="img/site/end.png">

</div>