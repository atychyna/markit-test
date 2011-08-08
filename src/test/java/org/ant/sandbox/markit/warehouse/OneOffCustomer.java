package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
final class OneOffCustomer<P extends Product> implements Consumer<P> {
    private final Class<P> productType;
    private int orderQuantity;

    public OneOffCustomer(Class<P> productType, int orderQuantity) {
        this.productType = productType;
        this.orderQuantity = orderQuantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public int consume(Collection<P> products) {
        orderQuantity = products.size() > orderQuantity ? 0 : orderQuantity - products.size();
        return products.size();
    }

    public Class<P> getProductType() {
        return productType;
    }
}
