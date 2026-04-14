package com.qqlin.comparator;

import com.qqlin.tmall.pojo.Product;

import java.util.Comparator;

/**
 * Comparator for sorting products by price in ascending order.
 *
 * <p>Compares two Product objects based on their promotePrice field.
 * Products with lower prices appear first in sorted collections.</p>
 *
 * <p>Used with {@link Collections#sort(List, Comparator)} or
 * {@link Arrays#sort(Object[], Comparator)} for price-based sorting.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List&lt;Product&gt; products = new ArrayList&lt;&gt;(allProducts);
 * Collections.sort(products, new ProductPriceComparator());
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-21
 * @see Comparator
 * @see Product
 */
public class ProductPriceComparator implements Comparator<Product> {

    /**
     * Compares two products by their promote price.
     *
     * @param o1 the first product to compare
     * @param o2 the second product to compare
     * @return negative if o1's price is less than o2's, positive if greater, zero if equal
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getPromotePrice().compareTo(o2.getPromotePrice());
    }
}
