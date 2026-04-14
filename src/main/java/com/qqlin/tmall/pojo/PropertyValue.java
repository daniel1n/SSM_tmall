package com.qqlin.tmall.pojo;

/**
 * PropertyValue entity representing the actual value of a property for a specific product.
 * This is the concrete assignment of a Property (specification) to a Product with an actual value.
 * Example: Product "iPhone 15" has Property "RAM" with Value "8GB"
 */
public class PropertyValue {
    /** Unique identifier for the property value */
    private Integer id;

    /** Foreign key to the product this property value belongs to */
    private Integer pid;

    /** Foreign key to the property definition */
    private Integer ptid;

    /** The actual value assigned, e.g., "8GB", "Red", "Cotton" */
    private String value;

    /**
     * Non-database field: Associated Property object for display purposes
     */
    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
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

    public Integer getPtid() {
        return ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}