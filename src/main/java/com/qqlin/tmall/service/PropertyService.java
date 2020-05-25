package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Property;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
public interface PropertyService {
    void add(Property property);

    void delete(int id);

    void update(Property property);

    Property get(int id);

    List list(int cid);
}
