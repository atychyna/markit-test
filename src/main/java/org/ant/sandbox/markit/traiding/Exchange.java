package org.ant.sandbox.markit.traiding;

import java.util.Set;

/**
 * @author Anton Tychyna
 */
public interface Exchange {
    /**
     * Returns list of names of exchanges which receive all trading information
     * from this exchange.
     *
     * @return list of exchanges
     */
    Set<String> getOutgoingConnections();

    /**
     * Name of the exchange
     *
     * @return name
     */
    String getName();
}
