<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 确认订单页面 (结算页) --%>
<%-- 功能: 填写收货地址、选择配送方式、确认订单商品、提交订单 --%>
<%-- 依赖变量: ois (OrderItem 列表), total (订单总金额) --%>

<div class="buyPageDiv">
    <%-- 订单表单 - 提交到 forecreateOrder 控制器 --%>
    <form action="forecreateOrder" method="post">

        <%-- 购买流程图 --%>
        <div class="buyFlow">
            <img class="pull-left" src="img/site/simpleLogo.png">
            <img class="pull-right" src="img/site/buyflow.png">
            <div style="clear:both"></div>
        </div>

        <%-- 收货地址区域 --%>
        <div class="address">
            <div class="addressTip">输入收货地址</div>
            <div>

                <%-- 地址表单 --%>
                <table class="addressTable">
                    <tr>
                        <td class="firstColumn">详细地址<span class="redStar">*</span></td>
                        <td><textarea name="address" placeholder="建议您如实填写详细收货地址，例如接到名称，门牌好吗，楼层和房间号等信息"></textarea></td>
                    </tr>
                    <tr>
                        <td>邮政编码</td>
                        <td><input name="post" placeholder="如果您不清楚邮递区号，请填写000000" type="text"></td>
                    </tr>
                    <tr>
                        <td>收货人姓名<span class="redStar">*</span></td>
                        <td><input name="receiver" placeholder="长度不超过25个字符" type="text"></td>
                    </tr>
                    <tr>
                        <td>手机号码 <span class="redStar">*</span></td>
                        <td><input name="mobile" placeholder="请输入11位手机号码" type="text"></td>
                    </tr>
                </table>

            </div>

        </div>

        <%-- 商品列表区域 --%>
        <div class="productList">
            <div class="productListTip">确认订单信息</div>

            <%-- 商品明细表格 --%>
            <table class="productListTable">
                <thead>
                <tr>
                    <th colspan="2" class="productListTableFirstColumn">
                        <img class="tmallbuy" src="img/site/tmallbuy.png">
                        <a class="marketLink" href="#nowhere">店铺：天猫店铺</a>
                        <a class="wangwanglink" href="#nowhere"> <span class="wangwangGif"></span> </a>
                    </th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>小计</th>
                    <th>配送方式</th>
                </tr>
                <%-- 分隔行 --%>
                <tr class="rowborder">
                    <td colspan="2"></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </thead>
                <tbody class="productListTableTbody">
                <c:forEach items="${ois}" var="oi" varStatus="st">
                    <tr class="orderItemTR">
                        <%-- 商品图片 --%>
                        <td class="orderItemFirstTD"><img class="orderItemImg"
                                                          src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
                        </td>
                        <%-- 商品信息 --%>
                        <td class="orderItemProductInfo">
                            <a href="foreproduct?pid=${oi.product.id}" class="orderItemProductLink">
                                    ${oi.product.name}
                            </a>

                            <img src="img/site/creditcard.png" title="支持信用卡支付">
                            <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                            <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">

                        </td>
                        <%-- 单价 --%>
                        <td>

                            <span class="orderItemProductPrice">￥<fmt:formatNumber type="number"
                                                                                   value="${oi.product.promotePrice}"
                                                                                   minFractionDigits="2"/></span>
                        </td>
                        <%-- 数量 --%>
                        <td>
                            <span class="orderItemProductNumber">${oi.number}</span>
                        </td>
                        <%-- 小计 --%>
                        <td><span class="orderItemUnitSum">
                        ￥<fmt:formatNumber type="number" value="${oi.number*oi.product.promotePrice}"
                                           minFractionDigits="2"/>
                        </span></td>
                        <%-- 配送方式 (仅第一行显示，跨越5行) --%>
                        <c:if test="${st.count==1}">
                            <td rowspan="5" class="orderItemLastTD">
                                <label class="orderItemDeliveryLabel">
                                    <input type="radio" value="" checked="checked">
                                    普通配送
                                </label>

                                <select class="orderItemDeliverySelect" class="form-control">
                                    <option>快递 免邮费</option>
                                </select>

                            </td>
                        </c:if>

                    </tr>
                </c:forEach>

                </tbody>

            </table>

            <%-- 订单备注区域 --%>
            <div class="orderItemSumDiv">
                <div class="pull-left">
                    <span class="leaveMessageText">给卖家留言:</span>
                    <span>
                    <img class="leaveMessageImg" src="img/site/leaveMessage.png">
                </span>
                    <span class="leaveMessageTextareaSpan">
                    <textarea name="userMessage" class="leaveMessageTextarea"></textarea>
                    <div>
                        <span>还可以输入200个字符</span>
                    </div>
                </span>
                </div>

                <%-- 店铺合计 --%>
                <span class="pull-right">店铺合计(含运费): ￥<fmt:formatNumber type="number" value="${total}"
                                                                       minFractionDigits="2"/></span>
            </div>

        </div>

        <%-- 实付款金额 --%>
        <div class="orderItemTotalSumDiv">
            <div class="pull-right">
                <span>实付款：</span>
                <span class="orderItemTotalSumSpan">￥<fmt:formatNumber type="number" value="${total}"
                                                                       minFractionDigits="2"/></span>
            </div>
        </div>

        <%-- 提交订单按钮 --%>
        <div class="submitOrderDiv">
            <button type="submit" class="submitOrderButton">提交订单</button>
        </div>
    </form>
</div>