package org.ant.sandbox.markit.warehouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    public Collection<P> consume(Collection<P> products) {
        List<P> leftOver = new ArrayList<P>();
        int productsToConsume = Math.min(products.size(), orderQuantity);
        orderQuantity -= productsToConsume;
        if (productsToConsume < products.size()) {
            Iterator<P> iter = products.iterator();
            for (int i = 0; i < products.size(); i++) {
                P p = iter.next();
                if (i >= productsToConsume) {
                    leftOver.add(p);
                }
            }
        }
        return leftOver;
    }

    public Class<P> getProductType() {
        return productType;
    }
}
