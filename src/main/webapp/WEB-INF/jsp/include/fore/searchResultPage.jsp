<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 搜索结果页面组件 --%>
<%-- 功能: 显示关键词搜索匹配的商品列表 --%>
<%-- 依赖变量: ps (商品列表，由 ForeController.search 填充) --%>

<div id="searchResult">

    <div class="searchResultDiv">
        <%-- 搜索商品列表组件 --%>
        <%@include file="productsBySearch.jsp" %>
    </div>

</div>