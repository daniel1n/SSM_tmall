<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%-- 商品评价区域组件 --%>
<%-- 功能: 显示商品评价列表 --%>
<%-- 与 productDetail.jsp 互斥显示，点击导航项切换 --%>
<%-- 依赖变量: p (商品对象), reviews (评价列表) --%>

<div class="productReviewDiv">
    <%-- 详情/评价 切换标签 --%>
    <div class="productReviewTopPart">
        <a href="#nowhere" class="productReviewTopPartSelectedLink">商品详情</a>
        <a href="#nowhere" class="selected">累计评价 <span class="productReviewTopReviewLinkNumber">${p.reviewCount}</span>
        </a>
    </div>

    <%-- 评价内容区域 --%>
    <div class="productReviewContentPart">
        <c:forEach items="${reviews}" var="r">
            <div class="productReviewItem">

                <%-- 评价内容 --%>
                <div class="productReviewItemDesc">
                    <div class="productReviewItemContent">
                            ${r.content }
                    </div>
                    <%-- 评价日期 --%>
                    <div class="productReviewItemDate"><fmt:formatDate value="${r.createDate}"
                                                                       pattern="yyyy-MM-dd"/></div>
                </div>
                <%-- 评价用户信息 --%>
                <div class="productReviewItemUserInfo">

                        ${r.user.anonymousName}<span class="userInfoGrayPart">（匿名）</span>
                </div>

                <div style="clear:both"></div>

            </div>
        </c:forEach>
    </div>

</div>