package com.qqlin.comparator;

import com.qqlin.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @author qqlin
 * @date 2020-5-21
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o1.getPromotePrice() - o2.getPromotePrice());
    }
}
