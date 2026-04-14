<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 购物车页面 --%>
<%-- 功能: 查看购物车商品、修改数量、删除商品、勾选下单 --%>
<%-- 依赖变量: ois (OrderItem 列表，由 ForeController.cart 填充) --%>

<script>
    // 是否确认删除标记
    var deleteOrderItem = false;
    // 要删除的订单项ID
    var deleteOrderItemid = 0;

    /**
     * 页面加载完成后初始化
     * 1. 删除订单项按钮事件
     * 2. 商品勾选状态切换
     * 3. 全选/取消全选
     * 4. 数量修改 (+/- 按钮、手动输入)
     * 5. 结算按钮状态同步
     */
    $(function () {

        // 删除订单项 - 点击后弹出确认框
        $("a.deleteOrderItem").click(function () {
            deleteOrderItem = false;
            var oiid = $(this).attr("oiid")
            deleteOrderItemid = oiid;
            $("#deleteConfirmModal").modal('show');
        });
        // 确认删除按钮
        $("button.deleteConfirmButton").click(function () {
            deleteOrderItem = true;
            $("#deleteConfirmModal").modal('hide');
        });
        // 模态框关闭后执行删除
        $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
            if (deleteOrderItem) {
                var page = "foredeleteOrderItem";
                $.post(
                    page,
                    {"oiid": deleteOrderItemid},
                    function (result) {
                        if ("success" == result) {
                            $("tr.cartProductItemTR[oiid=" + deleteOrderItemid + "]").hide();
                        } else {
                            location.href = "loginPage";
                        }
                    }
                );

            }
        })

        // 商品勾选状态切换
        $("img.cartProductItemIfSelected").click(function () {
            var selectit = $(this).attr("selectit")
            if ("selectit" == selectit) {
                $(this).attr("src", "img/site/cartNotSelected.png");
                $(this).attr("selectit", "false")
                $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
            } else {
                $(this).attr("src", "img/site/cartSelected.png");
                $(this).attr("selectit", "selectit")
                $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
            }
            syncSelect();  // 同步全选状态
            syncCreateOrderButton();  // 同步结算按钮
            calcCartSumPriceAndNumber();  // 计算总金额
        });

        // 全选/取消全选
        $("img.selectAllItem").click(function () {
            var selectit = $(this).attr("selectit")
            if ("selectit" == selectit) {
                $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
                $("img.selectAllItem").attr("selectit", "false")
                $(".cartProductItemIfSelected").each(function () {
                    $(this).attr("src", "img/site/cartNotSelected.png");
                    $(this).attr("selectit", "false");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
                });
            } else {
                $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
                $("img.selectAllItem").attr("selectit", "selectit")
                $(".cartProductItemIfSelected").each(function () {
                    $(this).attr("src", "img/site/cartSelected.png");
                    $(this).attr("selectit", "selectit");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
                });
            }
            syncCreateOrderButton();
            calcCartSumPriceAndNumber();

        });

        // 手动输入数量
        $(".orderItemNumberSetting").keyup(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();

            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num = parseInt(num);
            if (isNaN(num))
                num = 1;
            if (num <= 0)
                num = 1;
            if (num > stock)
                num = stock;

            syncPrice(pid, num, price);  // 同步价格到服务器并更新UI
        });

        // 数量增加按钮
        $(".numberPlus").click(function () {

            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();

            num++;
            if (num > stock)
                num = stock;
            syncPrice(pid, num, price);
        });

        // 数量减少按钮
        $(".numberMinus").click(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();

            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            --num;
            if (num <= 0)
                num = 1;
            syncPrice(pid, num, price);
        });

        // 结算按钮 - 跳转结算页面
        $("button.createOrderButton").click(function () {
            var params = "";
            $(".cartProductItemIfSelected").each(function () {
                if ("selectit" == $(this).attr("selectit")) {
                    var oiid = $(this).attr("oiid");
                    params += "&oiid=" + oiid;
                }
            });
            params = params.substring(1);
            location.href = "forebuy?" + params;
        });

    })

    /**
     * 同步结算按钮状态
     * 如果至少有一个商品被选中，启用结算按钮；否则禁用
     */
    function syncCreateOrderButton() {
        var selectAny = false;
        $(".cartProductItemIfSelected").each(function () {
            if ("selectit" == $(this).attr("selectit")) {
                selectAny = true;
            }
        });

        if (selectAny) {
            $("button.createOrderButton").css("background-color", "#C40000");
            $("button.createOrderButton").removeAttr("disabled");
        } else {
            $("button.createOrderButton").css("background-color", "#AAAAAA");
            $("button.createOrderButton").attr("disabled", "disabled");
        }

    }

    /**
     * 同步全选状态
     * 如果所有商品都被选中，则全选框也选中；否则取消选中
     */
    function syncSelect() {
        var selectAll = true;
        $(".cartProductItemIfSelected").each(function () {
            if ("false" == $(this).attr("selectit")) {
                selectAll = false;
            }
        });

        if (selectAll)
            $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
        else
            $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");

    }

    /**
     * 计算购物车总金额和总数量
     * 仅计算已选中的商品
     */
    function calcCartSumPriceAndNumber() {
        var sum = 0;
        var totalNumber = 0;
        $("img.cartProductItemIfSelected[selectit='selectit']").each(function () {
            var oiid = $(this).attr("oiid");
            var price = $(".cartProductItemSmallSumPrice[oiid=" + oiid + "]").text();
            price = price.replace(/,/g, "");
            price = price.replace(/￥/g, "");
            sum += new Number(price);

            var num = $(".orderItemNumberSetting[oiid=" + oiid + "]").val();
            totalNumber += new Number(num);

        });

        $("span.cartSumPrice").html("￥" + formatMoney(sum));
        $("span.cartTitlePrice").html("￥" + formatMoney(sum));
        $("span.cartSumNumber").html(totalNumber);
    }

    /**
     * 同步单个商品的价格和小计
     * @param pid 商品ID
     * @param num 购买数量
     * @param price 单价
     */
    function syncPrice(pid, num, price) {
        // 更新输入框数量
        $(".orderItemNumberSetting[pid=" + pid + "]").val(num);
        // 计算并显示小计
        var cartProductItemSmallSumPrice = formatMoney(num * price);
        $(".cartProductItemSmallSumPrice[pid=" + pid + "]").html("￥" + cartProductItemSmallSumPrice);
        // 重新计算总金额
        calcCartSumPriceAndNumber();

        // 同步到服务器
        var page = "forechangeOrderItem";
        $.post(
            page,
            {"pid": pid, "number": num},
            function (result) {
                if ("success" != result) {
                    location.href = "login.jsp";
                }
            }
        );

    }
</script>

<title>购物车</title>
<%-- 购物车主容器 --%>
<div class="cartDiv">
    <%-- 顶部购物车标题栏 --%>
    <div class="cartTitle pull-right">
        <span>已选商品  (不含运费)</span>
        <span class="cartTitlePrice">￥0.00</span>
        <button class="createOrderButton" disabled="disabled">结 算</button>
    </div>

    <%-- 商品列表表格 --%>
    <div class="cartProductList">
        <table class="cartProductTable">
            <thead>
            <tr>
                <th class="selectAndImage">
                    <%-- 全选复选框 --%>
                    <img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
                    全选

                </th>
                <th>商品信息</th>
                <th>单价</th>
                <th>数量</th>
                <th width="120px">金额</th>
                <th class="operation">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ois }" var="oi">
                <%-- 每行对应一个购物车商品 --%>
                <tr oiid="${oi.id}" class="cartProductItemTR">
                    <td>
                        <%-- 商品勾选框 --%>
                        <img selectit="false" oiid="${oi.id}" class="cartProductItemIfSelected"
                             src="img/site/cartNotSelected.png">
                        <a style="display:none" href="#nowhere"><img src="img/site/cartSelected.png"></a>
                        <%-- 商品图片 --%>
                        <img class="cartProductImg"
                             src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
                    </td>
                    <td>
                        <div class="cartProductLinkOutDiv">
                            <a href="foreproduct?pid=${oi.product.id}" class="cartProductLink">${oi.product.name}</a>
                            <div class="cartProductLinkInnerDiv">
                                <img src="img/site/creditcard.png" title="支持信用卡支付">
                                <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
                            </div>
                        </div>

                    </td>
                    <td>
                        <%-- 原价和促销价 --%>
                        <span class="cartProductItemOringalPrice">￥${oi.product.originalPrice}</span>
                        <span class="cartProductItemPromotionPrice">￥${oi.product.promotePrice}</span>

                    </td>
                    <td>
                        <%-- 数量调整区域 --%>
                        <div class="cartProductChangeNumberDiv">
                            <%-- 库存和价格信息 (隐藏，用于数量校验) --%>
                            <span class="hidden orderItemStock " pid="${oi.product.id}">${oi.product.stock}</span>
                            <span class="hidden orderItemPromotePrice "
                                  pid="${oi.product.id}">${oi.product.promotePrice}</span>
                            <%-- 数量减少按钮 --%>
                            <a pid="${oi.product.id}" class="numberMinus" href="#nowhere">-</a>
                            <%-- 数量输入框 --%>
                            <input pid="${oi.product.id}" oiid="${oi.id}" class="orderItemNumberSetting"
                                   autocomplete="off" value="${oi.number}">
                            <%-- 数量增加按钮 --%>
                            <a stock="${oi.product.stock}" pid="${oi.product.id}" class="numberPlus"
                               href="#nowhere">+</a>
                        </div>

                    </td>
                    <td>
                            <%-- 商品小计 --%>
                            <span class="cartProductItemSmallSumPrice" oiid="${oi.id}" pid="${oi.product.id}">
                            ￥<fmt:formatNumber type="number" value="${oi.product.promotePrice*oi.number}"
                                               minFractionDigits="2"/>
                            </span>

                    </td>
                    <td>
                        <%-- 删除按钮 --%>
                        <a class="deleteOrderItem" oiid="${oi.id}" href="#nowhere">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>

    <%-- 底部购物车操作栏 --%>
    <div class="cartFoot">
        <img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
        <span>全选</span>

        <div class="pull-right">
            <span>已选商品 <span class="cartSumNumber">0</span> 件</span>

            <span>合计 (不含运费): </span>
            <span class="cartSumPrice">￥0.00</span>
            <button class="createOrderButton" disabled="disabled">结 算</button>
        </div>

    </div>

</div>