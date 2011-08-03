package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
public interface Supplier<P extends Product> {
    int getStockQuantity();

    Collection<P> supply(int quantity);
}
