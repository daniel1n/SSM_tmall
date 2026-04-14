package com.qqlin.tmall.pojo;

/**
 * Property entity representing a specification/attribute that products can have.
 * Properties are defined at the Category level and specify what attributes products
 * in that category can have (e.g., "Color", "Size", "Material", "RAM" for electronics).
 * Examples: "Screen Size", "Storage Capacity", "Color", "Material"
 */
public class Property {
    /** Unique identifier for the property */
    private Integer id;

    /** Foreign key to the category this property belongs to */
    private Integer cid;

    /** Property name, e.g., "Color", "Size", "RAM" */
    private String name;

    /**
     * Non-database field: Associated Category object for display purposes
     */
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}