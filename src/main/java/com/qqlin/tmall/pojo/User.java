package com.qqlin.tmall.pojo;

/**
 * User entity representing a customer in the e-commerce system.
 * Users can browse products, write reviews, place orders, and manage their account.
 * Authentication is typically done via name (username) and password.
 */
public class User {
    /** Unique identifier for the user */
    private Integer id;

    /** Username for login and display purposes */
    private String name;

    /** User's password (typically encrypted in production) */
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAnonymousName() {
        if (null == name) {
            return null;
        }
        if (name.length() <= 1) {
            return "*";
        }
        if (name.length() == 2) {
            return name.substring(0, 1) + "*";
        }

        char[] chars = name.toCharArray();
        for (int i = 1; i < chars.length - 1; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }
}