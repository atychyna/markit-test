package org.ant.sandbox.markit.warehouse;

import java.util.Collection;

/**
 * @author Anton Tychyna
 */
public interface Consumer<P extends Product> {
    int getOrder();

    void consume(Collection<P> products);
}
