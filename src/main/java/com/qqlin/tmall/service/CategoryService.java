package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Category;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-18
 */
public interface CategoryService {

    List<Category> list();

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category category);
}
