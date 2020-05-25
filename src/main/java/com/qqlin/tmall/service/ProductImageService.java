package com.qqlin.tmall.service;

import com.qqlin.tmall.pojo.ProductImage;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
public interface ProductImageService {

    /**
     * 提供了两个常量，分别表示单个图片和详情图片
     */
    String type_single = "type_single";
    String type_detail = "type_detail";

    void add(ProductImage pi);

    void delete(int id);

    void update(ProductImage pi);

    ProductImage get(int id);

    /**
     * 根据产品id和图片类型查询的list方法
     *
     * @param pid
     * @param type
     * @return
     */
    List list(int pid, String type);
}
