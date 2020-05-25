package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
public interface PropertyValueService {
    void init(Product product);

    void update(PropertyValue propertyValue);

    PropertyValue get(int ptid, int pid);

    List<PropertyValue> list(int pid);
}
