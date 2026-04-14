<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 分类导航 + 轮播图组合组件 --%>
<%-- 功能: 左侧分类菜单 + 鼠标悬停显示侧边推荐商品 + 顶部固定导航 + 轮播图 --%>
<%-- 依赖变量: cs (categories 列表) --%>

<script>
    /**
     * 显示分类侧边推荐商品
     * @param cid 分类ID，用于定位对应的侧边推荐商品区域
     */
    function showProductsAsideCategorys(cid) {
        $("div.eachCategory[cid=" + cid + "]").css("background-color", "white");
        $("div.eachCategory[cid=" + cid + "] a").css("color", "#87CEFA");
        $("div.productsAsideCategorys[cid=" + cid + "]").show();
    }

    /**
     * 隐藏分类侧边推荐商品
     * @param cid 分类ID
     */
    function hideProductsAsideCategorys(cid) {
        $("div.eachCategory[cid=" + cid + "]").css("background-color", "#e2e2e3");
        $("div.eachCategory[cid=" + cid + "] a").css("color", "#000");
        $("div.productsAsideCategorys[cid=" + cid + "]").hide();
    }

    /**
     * 页面加载完成后初始化
     * 1. 分类菜单项鼠标事件绑定
     * 2. 侧边推荐商品区域鼠标事件绑定
     * 3. 顶部导航菜单鼠标跟随效果 (猫耳效果)
     * 4. 计算并设置各元素位置
     */
    $(function () {
        // 分类菜单项 - 鼠标进入显示侧边推荐
        $("div.eachCategory").mouseenter(function () {
            var cid = $(this).attr("cid");
            showProductsAsideCategorys(cid);
        });
        // 分类菜单项 - 鼠标离开隐藏侧边推荐
        $("div.eachCategory").mouseleave(function () {
            var cid = $(this).attr("cid");
            hideProductsAsideCategorys(cid);
        });

        // 侧边推荐商品区域 - 鼠标进入保持显示
        $("div.productsAsideCategorys").mouseenter(function () {
            var cid = $(this).attr("cid");
            showProductsAsideCategorys(cid);
        });
        // 侧边推荐商品区域 - 鼠标离开隐藏
        $("div.productsAsideCategorys").mouseleave(function () {
            var cid = $(this).attr("cid");
            hideProductsAsideCategorys(cid);
        });

        // 顶部右侧导航菜单 - 鼠标进入显示跟随猫耳效果
        $("div.rightMenu span").mouseenter(function () {
            var left = $(this).position().left;
            var top = $(this).position().top;
            var width = $(this).css("width");
            var destLeft = parseInt(left) + parseInt(width) / 2;
            $("img#catear").css("left", destLeft);
            $("img#catear").css("top", top - 20);
            $("img#catear").fadeIn(500);

        });
        // 顶部右侧导航菜单 - 鼠标离开隐藏猫耳
        $("div.rightMenu span").mouseleave(function () {
            $("img#catear").hide();
        });

        // 计算并设置分类菜单和侧边推荐商品的位置，与轮播图对齐
        var left = $("div#carousel-of-product").offset().left;
        $("div.categoryMenu").css("left", left - 20);
        $("div.categoryWithCarousel div.head").css("margin-left", left);
        $("div.productsAsideCategorys").css("left", left - 20);

    });
</script>

<%-- 猫耳图标 - 鼠标悬停导航时显示的跟随效果 --%>
<img src="img/site/catear.png" id="catear" class="catear"/>

<%-- 分类+轮播图容器 --%>
<div class="categoryWithCarousel">

    <%-- 顶部导航栏 --%>
    <div class="headbar show1">
        <div class="head ">

            <span style="margin-left:10px" class="glyphicon glyphicon-th-list"></span>
            <span style="margin-left:10px">商品分类</span>

        </div>

        <%-- 右侧导航菜单 --%>
        <div class="rightMenu">
            <span><a href=""><img src="img/site/chaoshi.png"/></a></span>
            <span><a href=""><img src="img/site/guoji.png"/></a></span>
            <%-- 仅显示前4个分类 --%>
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count<=4}">
                <span>
                <a href="forecategory?cid=${c.id}">
                        ${c.name}
                </a></span>
                </c:if>
            </c:forEach>
        </div>

    </div>

    <%-- 左侧分类菜单 --%>
    <div style="position: relative">
        <%@include file="categoryMenu.jsp" %>
    </div>

    <%-- 分类侧边推荐商品区域 (初始隐藏，鼠标悬停时显示) --%>
    <div style="position: relative;left: 0;top: 0;">
        <%@include file="productsAsideCategorys.jsp" %>
    </div>

    <%-- 轮播图区域 --%>
    <%@include file="carousel.jsp" %>

    <%-- 轮播图背景 (半透明遮罩) --%>
    <div class="carouselBackgroundDiv">
    </div>

</div>