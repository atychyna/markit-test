package org.ant.sandbox.markit.warehouse;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Anton Tychyna
 */
final class OneOffSupplier<P extends Product> implements Supplier<P> {
    private final Class<P> productType;
    private final P sampleProduct;
    private int stockQuantity;

    public OneOffSupplier(Class<P> productType, P sampleProduct, int stockQuantity) {
        this.productType = productType;
        this.sampleProduct = sampleProduct;
        this.stockQuantity = stockQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Collection<P> supply(int quantity) {
        int q = quantity > stockQuantity ? stockQuantity : quantity;
        stockQuantity -= q;
        return Collections.nCopies(q, sampleProduct);
    }

    public Class<P> getProductType() {
        return productType;
    }
}
