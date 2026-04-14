package com.qqlin.tmall.pojo;

import java.util.Date;

/**
 * Review entity representing a customer's product review/feedback.
 * Reviews are posted after purchase and contain the user's opinion about the product.
 * Reviews help other customers make purchasing decisions.
 */
public class Review {
    /** Unique identifier for the review */
    private Integer id;

    /** Review text content/comment */
    private String content;

    /** Foreign key to the user who wrote the review */
    private Integer uid;

    /** Foreign key to the product being reviewed */
    private Integer pid;

    /** Date when the review was posted */
    private Date createDate;

    /**
     * Non-database field: Associated User object for display purposes
     */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}