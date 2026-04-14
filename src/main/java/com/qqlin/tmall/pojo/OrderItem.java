package com.qqlin.tmall.pojo;

/**
 * OrderItem entity representing a single product line item within an Order.
 * An OrderItem tracks which product was purchased, in what quantity, and for which user.
 * It serves as the link between Orders and Products.
 */
public class OrderItem {
    /** Unique identifier for the order item */
    private Integer id;

    /** Foreign key to the product being ordered */
    private Integer pid;

    /** Foreign key to the parent order (null if cart item) */
    private Integer oid;

    /** Foreign key to the user who owns this order item */
    private Integer uid;

    /** Quantity of this product in the order */
    private Integer number;

    /** Associated Product object for display and calculations */
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}