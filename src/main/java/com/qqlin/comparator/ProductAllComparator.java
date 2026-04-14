package com.qqlin.comparator;

import com.qqlin.tmall.pojo.Product;

import java.util.Comparator;

/**
 * Comparator for sorting products by combined ranking score (sales * reviews).
 *
 * <p>Compares two Product objects using a composite score calculated as:
 * score = saleCount * reviewCount. Products with higher combined popularity
 * appear first in sorted collections.</p>
 *
 * <p>This comparator provides a balanced ranking that considers both
 * sales volume and customer engagement (reviews). Products that have
 * both high sales and many reviews will rank highest.</p>
 *
 * <p>Used with {@link Collections#sort(List, Comparator)} or
 * {@link Arrays#sort(Object[], Comparator)} for popularity-based sorting.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List&lt;Product&gt; products = new ArrayList&lt;&gt;(allProducts);
 * Collections.sort(products, new ProductAllComparator());
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-21
 * @see Comparator
 * @see Product
 */
public class ProductAllComparator implements Comparator<Product> {

    /**
     * Compares two products by combined ranking score (sales * reviews).
     *
     * @param o1 the first product to compare
     * @param o2 the second product to compare
     * @return negative if o1 has higher score, positive if lower, zero if equal
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() * o2.getReviewCount() - o1.getSaleCount() * o2.getReviewCount();
    }
}
