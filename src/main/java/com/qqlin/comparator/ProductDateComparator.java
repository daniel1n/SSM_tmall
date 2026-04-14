package com.qqlin.comparator;

import com.qqlin.tmall.pojo.Product;

import java.util.Comparator;

/**
 * Comparator for sorting products by creation date with newest first.
 *
 * <p>Compares two Product objects based on their createDate field.
 * Products created more recently appear first in sorted collections
 * (descending order by date).</p>
 *
 * <p>Used with {@link Collections#sort(List, Comparator)} or
 * {@link Arrays#sort(Object[], Comparator)} for date-based sorting.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List&lt;Product&gt; products = new ArrayList&lt;&gt;(allProducts);
 * Collections.sort(products, new ProductDateComparator());
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-21
 * @see Comparator
 * @see Product
 */
public class ProductDateComparator implements Comparator<Product> {

    /**
     * Compares two products by creation date (newest first).
     *
     * @param o1 the first product to compare
     * @param o2 the second product to compare
     * @return negative if o1 is newer than o2, positive if o1 is older, zero if same date
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getCreateDate().compareTo(o1.getCreateDate());
    }
}
