<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 后台管理导航栏组件 --%>
<%-- 功能: 显示固定顶部导航栏，包含天猫Logo、分类管理、用户管理、订单管理链接 --%>

<div class="navitagorDiv">
	<%-- Bootstrap 导航栏 --%>
	<nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
		<%-- 天猫Logo --%>
		<img style="margin-left:10px;margin-right:0px" class="pull-left" src="img/site/tmallbuy.png" height="45px">
		<%-- 后台管理标题 --%>
		<a class="navbar-brand" href="#nowhere">天猫后台</a>

		<%-- 分类管理链接 --%>
		<a class="navbar-brand" href="admin_category_list">分类管理</a>
		<%-- 用户管理链接 --%>
		<a class="navbar-brand" href="admin_user_list">用户管理</a>
		<%-- 订单管理链接 --%>
		<a class="navbar-brand" href="admin_order_list">订单管理</a>
	</nav>
</div>