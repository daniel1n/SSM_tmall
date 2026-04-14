package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.User;

import java.util.List;

/**
 * Service interface for User business operations.
 * Handles user account management, authentication, and existence checks.
 *
 * @author qqlin
 * @date 2020-5-21
 */
public interface UserService {
    /**
     * Add a new user to the database.
     *
     * @param user User entity to add (name must be unique)
     */
    void add(User user);

    /**
     * Delete a user by ID.
     *
     * @param id User ID to delete
     */
    void delete(int id);

    /**
     * Update an existing user's information.
     *
     * @param user User entity with updated values (ID must exist)
     */
    void update(User user);

    /**
     * Get a single user by ID.
     *
     * @param id User ID
     * @return User entity, or null if not found
     */
    User get(int id);

    /**
     * Get all users ordered by ID ascending.
     *
     * @return List of all users in the system
     */
    List list();

    /**
     * Check if a username already exists in the system.
     *
     * @param name Username to check
     * @return true if username is already taken, false otherwise
     */
    boolean isExist(String name);

    /**
     * Authenticate a user with username and password.
     *
     * @param name     Username
     * @param password Plain-text password
     * @return User entity if credentials are valid, or null if authentication fails
     */
    User get(String name, String password);
}
