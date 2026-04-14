<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 简化版搜索栏组件 (用于商品详情页等非首页) --%>
<%-- 功能: Logo + 搜索框 + 热门搜索词链接 --%>
<%-- 与 search.jsp 相比，Logo 使用简化版本，位置靠右对齐 --%>
<%-- 依赖变量: cs (categories 列表) --%>

<div>
    <%-- 简化版 Logo --%>
    <a href="${contextPath}">
        <img id="simpleLogo" class="simpleLogo" src="img/site/simpleLogo.png">
    </a>

    <%-- 搜索表单 - 提交到 foresearch 控制器 --%>
    <form action="foresearch" method="post">
        <div class="simpleSearchDiv pull-right">
            <%-- 搜索输入框 --%>
            <input type="text" placeholder="平衡车 原汁机" name="keyword">
            <%-- 搜索按钮 --%>
            <button class="searchButton" type="submit">搜天猫</button>
            <%-- 热门搜索分类链接 (显示第8-11个分类) --%>
            <div class="searchBelow">
                <c:forEach items="${cs}" var="c" varStatus="st">
                    <c:if test="${st.count>=8 and st.count<=11}">
                    <span>
                        <a href="forecategory?cid=${c.id}">
                                ${c.name}
                        </a>
                        <c:if test="${st.count!=11}">
                            <span>|</span>
                        </c:if>
                    </span>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </form>
    <div style="clear:both"></div>
</div>