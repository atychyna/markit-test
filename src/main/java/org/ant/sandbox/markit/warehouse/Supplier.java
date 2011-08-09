package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
public interface Supplier<P extends Product> {
    /**
     * Get number of products available on stock
     *
     * @return number of products
     */
    int getStockQuantity();

    /**
     * Supply <code>quantity</code> number of products.
     *
     * @param quantity quantity, implementations should handle cases when quantity > stockQuantity
     * @return supplied products
     */
    Collection<P> supply(int quantity);

    /**
     * One supplier can only work with one type of product.
     *
     * @return product type
     */
    Class<P> getProductType();
}
