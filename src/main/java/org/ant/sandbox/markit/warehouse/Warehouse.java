package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
public interface Warehouse {
    void setSuppliers(Collection<Supplier> suppliers);

    void setConsumers(Collection<Consumer> suppliers);

    /**
     * Run daily operation (one cycle of supply-consume)
     */
    void runOperation();
}
