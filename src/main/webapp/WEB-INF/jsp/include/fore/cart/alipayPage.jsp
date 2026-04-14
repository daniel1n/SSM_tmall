<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 支付宝支付页面组件 (模拟) --%>
<%-- 功能: 显示支付金额二维码、确认支付按钮 --%>
<%-- 依赖变量: param.oid (订单ID), param.total (支付金额) --%>

<div class="aliPayPageDiv">
    <%-- Logo --%>
    <div class="aliPayPageLogo">
        <img class="pull-left" src="img/site/simpleLogo.png">
        <div style="clear:both"></div>
    </div>

    <%-- 支付金额显示 --%>
    <div>
        <span class="confirmMoneyText">扫一扫付款（元）</span>
        <span class="confirmMoney">
        ￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/></span>

    </div>

    <%-- 支付宝二维码图片 (模拟) --%>
    <div>
        <img class="aliPayImg" src="img/site/alipay2wei.png">
    </div>

    <%-- 确认支付按钮 - 跳转到支付成功页 --%>
    <div>
        <a href="forepayed?oid=${param.oid}&total=${param.total}">
            <button class="confirmPay">确认支付</button>
        </a>
    </div>

</div>