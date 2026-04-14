package com.qqlin.comparator;

import com.qqlin.tmall.pojo.Product;

import java.util.Comparator;

/**
 * Comparator for sorting products by review count in descending order.
 *
 * <p>Compares two Product objects based on their reviewCount field.
 * Products with more reviews appear first in sorted collections.</p>
 *
 * <p>Used with {@link Collections#sort(List, Comparator)} or
 * {@link Arrays#sort(Object[], Comparator)} for review-based sorting.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List&lt;Product&gt; products = new ArrayList&lt;&gt;(allProducts);
 * Collections.sort(products, new ProductReviewComparator());
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-21
 * @see Comparator
 * @see Product
 */
public class ProductReviewComparator implements Comparator<Product> {

    /**
     * Compares two products by review count (highest first).
     *
     * @param o1 the first product to compare
     * @param o2 the second product to compare
     * @return negative if o1 has more reviews, positive if fewer, zero if equal
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() - o1.getReviewCount();
    }
}
