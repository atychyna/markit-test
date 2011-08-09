package org.ant.sandbox.markit.traiding.impl;

import org.ant.sandbox.markit.traiding.Exchange;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Anton Tychyna
 */
public class ExchangeImpl implements Exchange {
    private Set<String> outgoingConnections = new HashSet<String>();
    private final String name;

    public ExchangeImpl(String name) {
        this.name = name;
    }

    public static ExchangeImpl from(String exchange) {
        return new ExchangeImpl(exchange);
    }

    public ExchangeImpl to(String... exchanges) {
        outgoingConnections.addAll(Arrays.asList(exchanges));
        return this;
    }

    public Set<String> getOutgoingConnections() {
        return outgoingConnections;
    }

    public String getName() {
        return name;
    }
}
