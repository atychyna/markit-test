package org.ant.sandbox.markit.warehouse;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

/**
 * @author Anton Tychyna
 */
@Test
public class WarehouseTest {
    public void testWarehouse() {
        Supplier<Apple> appleSupplier = new OneOffSupplier<Apple>(Apple.class, new Apple(), 10);
        Supplier<Orange> orangeSupplier = new OneOffSupplier<Orange>(Orange.class, new Orange(), 10);
        Consumer<Apple> appleConsumer = new OneOffCustomer<Apple>(Apple.class, 2);
        Consumer<Orange> orangeConsumer = new OneOffCustomer<Orange>(Orange.class, 3);

        Warehouse house = new WarehouseImpl();
        house.setSuppliers(Arrays.<Supplier>asList(appleSupplier, orangeSupplier));
        house.setConsumers(Arrays.<Consumer>asList(appleConsumer, orangeConsumer));
        house.runOperation();

        assertEquals(appleConsumer.getOrderQuantity(), 0);
        assertEquals(orangeConsumer.getOrderQuantity(), 0);
        assertEquals(appleSupplier.getStockQuantity(), 8);
        assertEquals(orangeSupplier.getStockQuantity(), 7);
    }

    private static final class Apple implements Product {
        public String getName() {
            return "apple";
        }
    }

    private static final class Orange implements Product {
        public String getName() {
            return "orange";
        }
    }
}
