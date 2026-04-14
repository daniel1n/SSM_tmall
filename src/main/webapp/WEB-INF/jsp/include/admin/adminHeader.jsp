<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 后台管理页面公共头部 --%>
<%-- 功能: 引入 jQuery、Bootstrap、后端管理样式表，定义表单验证函数 --%>

<!DOCTYPE html>
<html>

<head>
	<%-- jQuery 2.0.0 --%>
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<%-- Bootstrap 3.3.6 CSS --%>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<%-- Bootstrap 3.3.6 JS --%>
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<%-- 后台管理样式表 --%>
	<link href="css/back/style.css" rel="stylesheet">

<script>
/**
 * 表单验证工具函数
 */

/**
 * 验证输入框值是否为空
 * @param id 输入框ID
 * @param name 字段名称（用于错误提示）
 */
function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}

/**
 * 验证输入框值是否为数字
 * @param id 输入框ID
 * @param name 字段名称
 */
function checkNumber(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(isNaN(value)){
		alert(name+ "必须是数字");
		$("#"+id)[0].focus();
		return false;
	}

	return true;
}

/**
 * 验证输入框值是否为整数
 * @param id 输入框ID
 * @param name 字段名称
 */
function checkInt(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(parseInt(value)!=value){
		alert(name+ "必须是整数");
		$("#"+id)[0].focus();
		return false;
	}

	return true;
}

/**
 * 页面加载完成后初始化
 * 为所有带 deleteLink 属性的链接绑定确认删除对话框
 */
$(function(){
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确认要删除");
			if(confirmDelete)
				return true;
			return false;

		}
	});
})
</script>
</head>
<body>