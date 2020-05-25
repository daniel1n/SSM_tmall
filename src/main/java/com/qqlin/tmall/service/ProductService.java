package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.Product;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    List list(int cid);

    void setFirstProductImage(Product product);

    void fill(List<Category> categories);

    void fill(Category category);

    void fillByRow(List<Category> cs);

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword);
}
