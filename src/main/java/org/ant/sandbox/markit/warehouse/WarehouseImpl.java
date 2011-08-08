package org.ant.sandbox.markit.warehouse;

import java.util.*;

/**
 * Default implementation, only for test purpose.
 *
 * @author Anton Tychyna
 */
public class WarehouseImpl implements Warehouse {
    private Map<Class, Collection<Supplier>> suppliers = new HashMap<Class, Collection<Supplier>>();
    private Map<Class, Collection<Consumer>> consumers = new HashMap<Class, Collection<Consumer>>();

    public void setSuppliers(Collection<Supplier> suppliers) {
        for (Supplier supplier : suppliers) {
            Class productType = supplier.getProductType();
            if (!this.suppliers.containsKey(productType)) {
                this.suppliers.put(productType, new ArrayList<Supplier>());
            }
            this.suppliers.get(productType).add(supplier);
        }
    }

    public void setConsumers(Collection<Consumer> suppliers) {
        for (Consumer consumer : suppliers) {
            Class productType = consumer.getProductType();
            if (!this.consumers.containsKey(productType)) {
                this.consumers.put(productType, new ArrayList<Consumer>());
            }
            this.consumers.get(productType).add(consumer);
        }
    }

    public void runOperation() {
        // okay, let's see what kind of products consumers want
        for (Map.Entry<Class, Collection<Consumer>> ce : consumers.entrySet()) {
            // this kind
            Class productType = ce.getKey();
            // check if anybody supplies it
            if (suppliers.containsKey(productType)) {
                // if so try to fulfill every customer order
                for (Consumer c : ce.getValue()) {
                    Iterator<Supplier> i = suppliers.get(productType).iterator();
                    while (c.getOrderQuantity() > 0 && i.hasNext()) {
                        Supplier s = i.next();
                        if (s.getStockQuantity() > 0) {
                            c.consume(s.supply(s.getStockQuantity() <= c.getOrderQuantity() ? s.getStockQuantity() : c.getOrderQuantity()));
                        }
                    }
                }
            }
        }
    }
}
