<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 首页轮播图组件 --%>
<%-- 使用 Bootstrap Carousel 插件实现，自动播放4张轮播图 --%>
<%-- 轮播图片存放于 img/lunbo/ 目录 --%>
<div id="carousel-of-product" class="carousel-of-product carousel slide1" data-ride="carousel">
    <!-- 轮播图指示器 - 显示当前播放到第几张 -->
    <ol class="carousel-indicators">
        <li data-target="#carousel-of-product" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-of-product" data-slide-to="1"></li>
        <li data-target="#carousel-of-product" data-slide-to="2"></li>
        <li data-target="#carousel-of-product" data-slide-to="3"></li>
    </ol>

    <!-- 轮播图内容区域 - 4张图片循环播放 -->
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <%-- 第一张图片 - 默认选中 active --%>
            <img class="carousel carouselImage" src="img/lunbo/1.jpg">
        </div>
        <div class="item">
            <img class="carouselImage" src="img/lunbo/2.jpg">
        </div>
        <div class="item">
            <img class="carouselImage" src="img/lunbo/3.jpg">
        </div>

        <div class="item">
            <img class="carouselImage" src="img/lunbo/4.jpg">
        </div>

    </div>

    <!-- 左右切换按钮 (已注释禁用) -->
    <!-- 如需启用，移除 HTML 注释并保留对应的 CSS 样式 -->

</div>