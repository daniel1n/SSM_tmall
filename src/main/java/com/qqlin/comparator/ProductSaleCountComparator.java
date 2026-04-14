package com.qqlin.comparator;

import com.qqlin.tmall.pojo.Product;

import java.util.Comparator;

/**
 * Comparator for sorting products by sales count in descending order.
 *
 * <p>Compares two Product objects based on their saleCount field.
 * Products with higher sales counts appear first in sorted collections.</p>
 *
 * <p>Used with {@link Collections#sort(List, Comparator)} or
 * {@link Arrays#sort(Object[], Comparator)} for popularity-based sorting.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List&lt;Product&gt; products = new ArrayList&lt;&gt;(allProducts);
 * Collections.sort(products, new ProductSaleCountComparator());
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-21
 * @see Comparator
 * @see Product
 */
public class ProductSaleCountComparator implements Comparator<Product> {

    /**
     * Compares two products by sales count (highest first).
     *
     * @param o1 the first product to compare
     * @param o2 the second product to compare
     * @return negative if o1 has higher sales, positive if lower, zero if equal
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() - o1.getSaleCount();
    }
}
