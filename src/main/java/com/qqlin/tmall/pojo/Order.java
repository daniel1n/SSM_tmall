package com.qqlin.tmall.pojo;

import com.qqlin.tmall.service.OrderService;

import java.util.Date;
import java.util.List;

/**
 * Order entity representing a customer purchase transaction.
 * Orders go through a lifecycle: created -> paid -> delivered -> confirmed -> completed
 * Each order contains multiple OrderItems representing the products purchased.
 */
public class Order {
    /** Unique identifier for the order */
    private Integer id;

    /** Unique order code/number for tracking, e.g., "20231015-001" */
    private String orderCode;

    /** Delivery address street address */
    private String address;

    /** Postal/zip code for delivery */
    private String post;

    /** Name of the person receiving the order */
    private String receiver;

    /** Contact mobile phone number */
    private String mobile;

    /** Optional message from the user to the seller */
    private String userMessage;

    /** Date when the order was created */
    private Date createDate;

    /** Date when payment was received */
    private Date payDate;

    /** Date when the order was shipped/delivered */
    private Date deliveryDate;

    /** Date when the buyer confirmed receipt */
    private Date confirmDate;

    /** Foreign key to the user who placed the order */
    private Integer uid;

    /** Order status: waitPay, waitDelivery, waitConfirm, waitReview, finish, delete */
    private String status;

    /** List of items included in this order */
    private List<OrderItem> orderItems;

    /** Associated User object for display purposes */
    private User user;

    /** Total order amount */
    private float total;

    /** Total number of items in the order */
    private int totalNumber;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusDesc() {
        String desc = "未知";
        switch (status) {
            case OrderService.WAIT_PAY:
                desc = "待付款";
                break;
            case OrderService.WAIT_DELIVERY:
                desc = "待发货";
                break;
            case OrderService.WAIT_CONFIRM:
                desc = "待收货";
                break;
            case OrderService.WAIT_REVIEW:
                desc = "等评价";
                break;
            case OrderService.FINISH:
                desc = "完成";
                break;
            case OrderService.DELETE:
                desc = "刪除";
                break;
            default:
                desc = "未知";
        }
        return desc;
    }
}