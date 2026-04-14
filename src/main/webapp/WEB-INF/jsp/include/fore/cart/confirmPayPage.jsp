<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 确认支付页面组件 - 用户确认收货 --%>
<%-- 功能: 显示订单详情、物流时间轴、确认支付按钮 --%>
<%-- 依赖变量: o (Order 对象，包含 orderItems) --%>

<div class="confirmPayPageDiv">
    <%-- 物流时间轴图片 --%>
    <div class="confirmPayImageDiv">
        <img src="img/site/comformPayFlow.png">
        <%-- 时间节点1: 下单时间 --%>
        <div class="confirmPayTime1">
            <fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
        <%-- 时间节点2: 支付时间 --%>
        <div class="confirmPayTime2">
            <fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
        <%-- 时间节点3: 发货时间 --%>
        <div class="confirmPayTime3">
            <fmt:formatDate value="${o.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>

    </div>
    <%-- 确认付款标题 --%>
    <div class="confirmPayOrderInfoDiv">
        <div class="confirmPayOrderInfoText">我已收到货，同意支付宝付款</div>
    </div>

    <%-- 订单商品信息 --%>
    <div class="confirmPayOrderItemDiv">
        <div class="confirmPayOrderItemText">订单信息</div>
        <table class="confirmPayOrderItemTable">
            <thead>
            <th colspan="2">宝贝</th>
            <th width="120px">单价</th>
            <th width="120px">数量</th>
            <th width="120px">商品总价</th>
            <th width="120px">运费</th>
            </thead>
            <c:forEach items="${o.orderItems}" var="oi">
                <tr>
                    <%-- 商品图片 --%>
                    <td><img width="50px" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
                    <%-- 商品名称 --%>
                    <td class="confirmPayOrderItemProductLink">
                        <a href="#nowhere">${oi.product.name}</a>
                    </td>
                    <%-- 原价 --%>
                    <td>￥<fmt:formatNumber type="number" value="${oi.product.originalPrice}"
                                           minFractionDigits="2"/></td>
                    <td>1</td>
                    <%-- 促销价小计 --%>
                    <td><span class="conformPayProductPrice">￥<fmt:formatNumber type="number"
                                                                                value="${oi.product.promotePrice}"
                                                                                minFractionDigits="2"/></span></td>
                    <td><span>快递 ： 0.00 </span></td>
                </tr>
            </c:forEach>
        </table>

        <%-- 实付款金额 --%>
        <div class="confirmPayOrderItemText pull-right">
            实付款： <span class="confirmPayOrderItemSumPrice">￥<fmt:formatNumber type="number" value="${o.total}"
                                                                              minFractionDigits="2"/></span>
        </div>

    </div>

    <%-- 订单详细信息 --%>
    <div class="confirmPayOrderDetailDiv">

        <table class="confirmPayOrderDetailTable">
            <tr>
                <td>订单编号：</td>
                <td>${o.orderCode} <img width="23px" src="img/site/confirmOrderTmall.png"></td>
            </tr>
            <tr>
                <td>卖家昵称：</td>
                <td>天猫商铺 <span class="confirmPayOrderDetailWangWangGif"></span></td>
            </tr>
            <tr>
                <td>收货信息：</td>
                <td>${o.address}，${o.receiver}， ${o.mobile}，${o.post} </td>
            </tr>
            <tr>
                <td>成交时间：</td>
                <td><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </table>

    </div>

    <%-- 确认支付按钮 --%>
    <div class="confirmPayButtonDiv">
        <div class="confirmPayWarning">请收到货后，再确认收货！否则您可能钱货两空！</div>
        <a href="foreorderConfirmed?oid=${o.id}">
            <button class="confirmPayButton">确认支付</button>
        </a>
    </div>
</div>