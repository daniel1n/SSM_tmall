<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 登录页面片段 - 被 login.jsp 包含 --%>
<%-- 功能: 用户登录表单，包含错误提示、输入验证 --%>
<%-- 依赖变量: msg (错误信息，由控制器设置) --%>

<script>
    /**
     * 页面加载完成后初始化
     * 1. 显示错误信息 (如果存在)
     * 2. 表单验证: 用户名和密码不能为空
     * 3. 输入框键盘事件: 隐藏错误信息
     */
    $(function () {

        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.registerErrorMessageDiv").css("visibility", "visible");
        </c:if>

        // 注册表单提交前验证
        $(".registerForm").submit(function () {
            // 用户名不能为空
            if (0 == $("#name").val().length) {
                $("span.errorMessage").html("请输入用户名");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }
            // 密码不能为空
            if (0 == $("#password").val().length) {
                $("span.errorMessage").html("请输入密码");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }
            // 重复密码不能为空
            if (0 == $("#repeatpassword").val().length) {
                $("span.errorMessage").html("请输入重复密码");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }
            // 两次密码必须一致
            if ($("#password").val() != $("#repeatpassword").val()) {
                $("span.errorMessage").html("重复密码不一致");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }

            return true;
        });
    })
</script>

<%-- 注册表单 - 提交到 foreregister 控制器 --%>
<form method="post" action="foreregister" class="registerForm">

    <div class="registerDiv">
        <%-- 错误信息显示区域 --%>
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>

        <%-- 注册表单表格 --%>
        <table class="registerTable" align="center">
            <%-- 设置会员名提示 --%>
            <tr>
                <td class="registerTip registerTableLeftTD">设置会员名</td>
                <td></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登陆名</td>
                <td class="registerTableRightTD"><input id="name" name="name" placeholder="会员名一旦设置成功，无法修改"></td>
            </tr>
            <%-- 设置登录密码提示 --%>
            <tr>
                <td class="registerTip registerTableLeftTD">设置登陆密码</td>
                <td class="registerTableRightTD">登陆时验证，保护账号信息</td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登陆密码</td>
                <td class="registerTableRightTD"><input id="password" name="password" type="password"
                                                        placeholder="设置你的登陆密码"></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">密码确认</td>
                <td class="registerTableRightTD"><input id="repeatpassword" type="password" placeholder="请再次输入你的密码">
                </td>
            </tr>

            <%-- 提交按钮 --%>
            <tr>
                <td colspan="2" class="registerButtonTD">
                    <a href="registerSuccess.jsp">
                        <button>提 交</button>
                    </a>
                </td>
            </tr>
        </table>
    </div>
</form>