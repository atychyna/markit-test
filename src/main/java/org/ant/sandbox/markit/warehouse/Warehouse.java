package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
public interface Warehouse {
    Collection<Supplier> getSuppliers();

    Collection<Supplier> getConsumers();

    void runOperation();
}
