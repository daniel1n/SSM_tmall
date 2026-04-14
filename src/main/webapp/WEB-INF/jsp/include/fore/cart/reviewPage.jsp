<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 商品评价页面组件 --%>
<%-- 功能: 显示商品信息、评价表单或历史评价列表 --%>
<%-- 依赖变量: p (商品), o (订单), reviews (评价列表), param.showonly (是否仅显示评价) --%>

<div class="reviewDiv">
    <%-- 商品信息区域 --%>
    <div class="reviewProductInfoDiv">
        <%-- 商品主图 --%>
        <div class="reviewProductInfoImg"><img width="400px" height="400px"
                                               src="img/productSingle/${p.firstProductImage.id}.jpg"></div>
        <div class="reviewProductInfoRightDiv">
            <%-- 商品名称 --%>
            <div class="reviewProductInfoRightText">
                ${p.name}
            </div>
            <%-- 商品价格等信息 --%>
            <table class="reviewProductInfoTable">
                <tr>
                    <td width="75px">价格:</td>
                    <td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number"
                                                                                     value="${p.originalPrice}"
                                                                                     minFractionDigits="2"/></span> 元
                    </td>
                </tr>
                <tr>
                    <td>配送</td>
                    <td>快递: 0.00</td>
                </tr>
                <tr>
                    <td>月销量:</td>
                    <td><span class="reviewProductInfoTableSellNumber">${p.saleCount}</span> 件</td>
                </tr>
            </table>

            <%-- 购买信息 --%>
            <div class="reviewProductInfoRightBelowDiv">
                <span class="reviewProductInfoRightBelowImg"><img1 src="img/site/reviewLight.png"></span>
                <span class="reviewProductInfoRightBelowText">现在查看的是 您所购买商品的信息
于<fmt:formatDate value="${o.createDate}" pattern="yyyy年MM月dd"/>下单购买了此商品 </span>

            </div>
        </div>
        <div style="clear:both"></div>
    </div>

    <%-- 评价统计区域 --%>
    <div class="reviewStasticsDiv">
        <div class="reviewStasticsLeft">
            <div class="reviewStasticsLeftTop"></div>
            <div class="reviewStasticsLeftContent">累计评价 <span class="reviewStasticsNumber"> ${p.reviewCount}</span>
            </div>
            <div class="reviewStasticsLeftFoot"></div>
        </div>
        <div class="reviewStasticsRight">
            <div class="reviewStasticsRightEmpty"></div>
            <div class="reviewStasticsFoot"></div>
        </div>
    </div>

    <%-- 仅显示评价模式 (showonly=true) --%>
    <c:if test="${param.showonly==true}">
        <div class="reviewDivlistReviews">
            <c:forEach items="${reviews}" var="r">
                <div class="reviewDivlistReviewsEach">
                    <%-- 评价日期 --%>
                    <div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd"/></div>
                    <%-- 评价内容 --%>
                    <div class="reviewContent">${r.content}</div>
                    <%-- 评价用户 (匿名显示) --%>
                    <div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <%-- 评价编辑模式 (showonly!=true) - 显示评价表单 --%>
    <c:if test="${param.showonly!=true}">
        <div class="makeReviewDiv">
            <%-- 评价表单 - 提交到 foredoreview --%>
            <form method="post" action="foredoreview">
                <div class="makeReviewText">其他买家，需要你的建议哦！</div>
                <table class="makeReviewTable">
                    <tr>
                        <td class="makeReviewTableFirstTD">评价商品</td>
                        <td><textarea name="content"></textarea></td>
                    </tr>
                </table>
                <%-- 隐藏字段: 订单ID和商品ID --%>
                <div class="makeReviewButtonDiv">
                    <input type="hidden" name="oid" value="${o.id}">
                    <input type="hidden" name="pid" value="${p.id}">
                    <button type="submit">提交评价</button>
                </div>
            </form>
        </div>
    </c:if>

</div>