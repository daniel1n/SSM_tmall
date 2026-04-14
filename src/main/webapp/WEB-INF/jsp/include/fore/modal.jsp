<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 模态框组件 - 包含登录模态框和删除确认模态框 --%>

<script>
    /**
     * 页面加载完成后初始化
     * 1. 显示错误信息 (如果存在)
     * 2. 表单验证 - 登录表单不能为空
     * 3. 输入框键盘事件 - 隐藏错误信息
     * 4. 模态框居中定位
     */
    $(function () {

        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.loginErrorMessageDiv").show();
        </c:if>

        // 登录表单提交前验证
        $("form.loginForm").submit(function () {
            if (0 == $("#name").val().length || 0 == $("#password").val().length) {
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });

        // 输入框输入时隐藏错误信息
        $("form.loginForm input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
        });

        // 模态框居中定位
        var left = window.innerWidth / 2 + 162;
        $("div.loginSmallDiv").css("left", left);
    })
</script>

<%-- 登录模态框 - 用于商品页等非登录状态下操作时弹出 --%>
<div id="loginDiv" style="position: relative">

    <%-- 简化版 Logo --%>
    <div class="simpleLogo">
        <a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
    </div>

    <%-- 登录背景图 --%>
    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">

    <%-- 登录表单 - 提交到 forelogin 控制器 --%>
    <form class="loginForm" action="forelogin" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <%-- 错误信息显示区域 --%>
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>

            <%-- 账户登录标题 --%>
            <div class="login_acount_text">账户登录</div>
            <%-- 用户名输入框 --%>
            <div class="loginInput ">
                <span class="loginInputIcon ">
                    <span class=" glyphicon glyphicon-user"></span>
                </span>
                <input id="name" name="name" placeholder="手机/会员名/邮箱" type="text">
            </div>

            <%-- 密码输入框 --%>
            <div class="loginInput ">
                <span class="loginInputIcon ">
                    <span class=" glyphicon glyphicon-lock"></span>
                </span>
                <input id="password" name="password" type="password" placeholder="密码" type="text">
            </div>
            <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>

            <%-- 忘记密码 / 免费注册链接 --%>
            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="registerPage" class="pull-right">免费注册</a>
            </div>
            <%-- 登录按钮 --%>
            <div style="margin-top:20px">
                <button class="btn btn-block redButton" type="submit">登录</button>
            </div>
        </div>
    </form>

</div>

<%-- 删除确认模态框 - 用于确认删除操作 --%>
<div class="modal" id="deleteConfirmModal" tabindex="-1" role="dialog">
    <div class="modal-dialog deleteConfirmModalDiv">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">确认删除？</h4>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                <button class="btn btn-primary deleteConfirmButton" id="submit" type="button">确认</button>
            </div>
        </div>
    </div>
</div>