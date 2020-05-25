package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Review;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
public interface ReviewService {
    void add(Review review);

    void delete(int id);

    void update(Review review);

    Review get(int id);

    List list(int pid);

    int getCount(int pid);
}
