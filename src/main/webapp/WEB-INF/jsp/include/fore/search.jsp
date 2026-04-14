<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 首页搜索栏组件 --%>
<%-- 功能: 搜索框 + 热门搜索词链接 --%>
<%-- 依赖变量: cs (categories 列表，用于显示热门分类) --%>

<%-- 网站 Logo --%>
<a href="${contextPath}">
    <img id="logo" src="img/site/logo.gif" class="logo">
</a>

<%-- 搜索表单 - 提交到 foresearch 控制器 --%>
<form action="foresearch" method="post">
    <div class="searchDiv">
        <%-- 搜索输入框 - placeholder 显示默认搜索提示 --%>
        <input name="keyword" type="text" placeholder="时尚男鞋  太阳镜 ">
        <%-- 搜索按钮 --%>
        <button type="submit" class="searchButton">搜索</button>
        <%-- 热门搜索分类链接 (显示第5-8个分类) --%>
        <div class="searchBelow">
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count>=5 and st.count<=8}">
                        <span>
                            <a href="forecategory?cid=${c.id}">
                                    ${c.name}
                            </a>
                            <%-- 非最后一个分类显示分隔符 --%>
                            <c:if test="${st.count!=8}">
                                <span>|</span>
                            </c:if>
                        </span>
                </c:if>
            </c:forEach>
        </div>
    </div>
</form>