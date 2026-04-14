<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 我的订单页面组件 --%>
<%-- 功能: 显示用户所有订单、支持按状态筛选、删除订单 --%>
<%-- 依赖变量: os (Order 列表，包含 orderItems) --%>

<script>
    // 是否确认删除订单标记
    var deleteOrder = false;
    // 要删除的订单ID
    var deleteOrderid = 0;

    /**
     * 页面加载完成后初始化
     * 1. 订单状态筛选 (全部/待付款/待发货/待收货/待评价)
     * 2. 删除订单按钮事件
     * 3. 催发货按钮事件
     */
    $(function () {
        // 订单状态筛选
        $("a[orderStatus]").click(function () {
            var orderStatus = $(this).attr("orderStatus");
            if ('all' == orderStatus) {
                $("table[orderStatus]").show();
            } else {
                $("table[orderStatus]").hide();
                $("table[orderStatus=" + orderStatus + "]").show();
            }

            $("div.orderType div").removeClass("selectedOrderType");
            $(this).parent("div").addClass("selectedOrderType");
        });

        // 删除订单 - 点击后弹出确认框
        $("a.deleteOrderLink").click(function () {
            deleteOrderid = $(this).attr("oid");
            deleteOrder = false;
            $("#deleteConfirmModal").modal("show");
        });

        // 确认删除按钮
        $("button.deleteConfirmButton").click(function () {
            deleteOrder = true;
            $("#deleteConfirmModal").modal('hide');
        });

        // 模态框关闭后执行删除
        $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
            if (deleteOrder) {
                var page = "foredeleteOrder";
                $.post(
                    page,
                    {"oid": deleteOrderid},
                    function (result) {
                        if ("success" == result) {
                            $("table.orderListItemTable[oid=" + deleteOrderid + "]").hide();
                        } else {
                            location.href = "login.jsp";
                        }
                    }
                );

            }
        })

        // 催发货按钮 - 模拟秒发
        $(".ask2delivery").click(function () {
            var link = $(this).attr("link");
            $(this).hide();
            page = link;
            $.ajax({
                url: page,
                success: function (result) {
                    alert("卖家已秒发，刷新当前页面，即可进行确认收货")
                }
            });

        });
    });

</script>

<%-- 订单容器 --%>
<div class="boughtDiv">
    <%-- 订单状态筛选栏 --%>
    <div class="orderType">
        <div class="selectedOrderType"><a orderStatus="all" href="#nowhere">所有订单</a></div>
        <div><a orderStatus="waitPay" href="#nowhere">待付款</a></div>
        <div><a orderStatus="waitDelivery" href="#nowhere">待发货</a></div>
        <div><a orderStatus="waitConfirm" href="#nowhere">待收货</a></div>
        <div><a orderStatus="waitReview" href="#nowhere" class="noRightborder">待评价</a></div>
        <div class="orderTypeLastOne"><a class="noRightborder"></a></div>
    </div>
    <div style="clear:both"></div>

    <%-- 订单列表标题栏 --%>
    <div class="orderListTitle">
        <table class="orderListTitleTable">
            <tr>
                <td>宝贝</td>
                <td width="100px">单价</td>
                <td width="100px">数量</td>
                <td width="120px">实付款</td>
                <td width="100px">交易操作</td>
            </tr>
        </table>

    </div>

    <%-- 订单列表 --%>
    <div class="orderListItem">
        <c:forEach items="${os}" var="o">
            <%-- 订单表格，orderStatus 属性用于状态筛选 --%>
            <table class="orderListItemTable" orderStatus="${o.status}" oid="${o.id}">
                <%-- 订单头部信息 --%>
                <tr class="orderListItemFirstTR">
                    <td colspan="2">
                        <b><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></b>
                        <span>订单号: ${o.orderCode}
                    </span>
                    </td>
                    <td colspan="2"><img width="13px" src="img/site/orderItemTmall.png">天猫商场</td>
                    <td colspan="1">
                        <a class="wangwanglink" href="#nowhere">
                            <div class="orderItemWangWangGif"></div>
                        </a>

                    </td>
                    <td class="orderItemDeleteTD">
                        <a class="deleteOrderLink" oid="${o.id}" href="#nowhere">
                            <span class="orderListItemDelete glyphicon glyphicon-trash"></span>
                        </a>

                    </td>
                </tr>
                <%-- 订单商品列表 --%>
                <c:forEach items="${o.orderItems}" var="oi" varStatus="st">
                    <tr class="orderItemProductInfoPartTR">
                        <%-- 商品图片 --%>
                        <td class="orderItemProductInfoPartTD"><img width="80" height="80"
                                                                    src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
                        </td>
                        <%-- 商品信息 --%>
                        <td class="orderItemProductInfoPartTD">
                            <div class="orderListItemProductLinkOutDiv">
                                <a href="foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
                                <div class="orderListItemProductLinkInnerDiv">
                                    <img src="img/site/creditcard.png" title="支持信用卡支付">
                                    <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                    <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
                                </div>
                            </div>
                        </td>
                        <%-- 单价 --%>
                        <td class="orderItemProductInfoPartTD" width="100px">

                            <div class="orderListItemProductOriginalPrice">￥<fmt:formatNumber type="number"
                                                                                              value="${oi.product.originalPrice}"
                                                                                              minFractionDigits="2"/></div>
                            <div class="orderListItemProductPrice">￥<fmt:formatNumber type="number"
                                                                                      value="${oi.product.promotePrice}"
                                                                                      minFractionDigits="2"/></div>

                        </td>
                        <%-- 数量、实付款、交易操作 (仅第一行显示) --%>
                        <c:if test="${st.count==1}">

                            <td valign="top" rowspan="${fn:length(o.orderItems)}"
                                class="orderListItemNumberTD orderItemOrderInfoPartTD" width="100px">
                                <span class="orderListItemNumber">${o.totalNumber}</span>
                            </td>
                            <td valign="top" rowspan="${fn:length(o.orderItems)}" width="120px"
                                class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD">
                                <div class="orderListItemProductRealPrice">￥<fmt:formatNumber minFractionDigits="2"
                                                                                              maxFractionDigits="2"
                                                                                              type="number"
                                                                                              value="${o.total}"/></div>
                                <div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
                            </td>
                            <td valign="top" rowspan="${fn:length(o.orderItems)}"
                                class="orderListItemButtonTD orderItemOrderInfoPartTD" width="100px">
                                <%-- 待收货状态: 显示确认收货按钮 --%>
                                <c:if test="${o.status=='waitConfirm' }">
                                    <a href="foreconfirmPay?oid=${o.id}">
                                        <button class="orderListItemConfirm">确认收货</button>
                                    </a>
                                </c:if>
                                <%-- 待付款状态: 显示付款按钮 --%>
                                <c:if test="${o.status=='waitPay' }">
                                    <a href="forealipay?oid=${o.id}&total=${o.total}">
                                        <button class="orderListItemConfirm">付款</button>
                                    </a>
                                </c:if>

                                <%-- 待发货状态: 显示待发货 --%>
                                <c:if test="${o.status=='waitDelivery' }">
                                    <span>待发货</span>
                                </c:if>

                                <%-- 待评价状态: 显示评价按钮 --%>
                                <c:if test="${o.status=='waitReview' }">
                                    <a href="forereview?oid=${o.id}">
                                        <button class="orderListItemReview">评价</button>
                                    </a>
                                </c:if>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>

            </table>
        </c:forEach>

    </div>

</div>