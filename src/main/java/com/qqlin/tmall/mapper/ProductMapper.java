package com.qqlin.tmall.mapper;

import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.ProductExample;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * Atomically decrease product stock.
     * Only succeeds if current stock >= requested number.
     *
     * @param pid Product ID
     * @param number Number of items to deduct
     * @return Number of rows affected (1 = success, 0 = insufficient stock)
     */
    int decreaseStock(Integer pid, Integer number);

    /**
     * Update product with optimistic lock check.
     * Only updates if version matches, preventing concurrent overwrites.
     *
     * @param product Product to update
     * @return Number of rows affected (1 = success, 0 = version conflict)
     */
    int updateWithOptimisticLock(Product product);
}