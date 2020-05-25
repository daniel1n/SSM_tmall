package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.User;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
public interface UserService {
    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List list();

    boolean isExist(String name);

    User get(String name, String password);
}
