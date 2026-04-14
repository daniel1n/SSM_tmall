<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 左侧分类导航菜单 --%>
<%-- 显示所有分类，点击分类名称跳转至分类商品页面 --%>
<%-- 依赖变量: cs (categories 列表，由 ForeController.home 或 OtherInterceptor 填充) --%>
<div class="categoryMenu">
    <c:forEach items="${cs}" var="c">
        <%-- eachCategory: 单个分类项，cid 属性用于鼠标悬停时显示侧边推荐商品 --%>
        <div cid="${c.id}" class="eachCategory">
            <span class="glyphicon glyphicon-link"></span>
            <a href="forecategory?cid=${c.id}">
                    ${c.name}
            </a>
        </div>
    </c:forEach>
</div>