package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
public interface Consumer<P extends Product> {
    /**
     * @return number of products this consumer can consume
     */
    int getOrderQuantity();

    /**
     * @param products products to consume
     * @return products that were not consumed (e.g. if more products than needed was supplied)
     */
    Collection<P> consume(Collection<P> products);

    /**
     * One consumer can only work with one type of product.
     *
     * @return product type
     */
    Class<P> getProductType();
}
