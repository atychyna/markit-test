package org.ant.sandbox.markit.trading;

import org.ant.sandbox.markit.traiding.Exchange;
import org.ant.sandbox.markit.traiding.TransparencyChecker;
import org.ant.sandbox.markit.traiding.impl.TransparencyCheckerImpl;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.ant.sandbox.markit.traiding.impl.ExchangeImpl.from;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Anton Tychyna
 */
@Test
public class TransparencyCheckerTest {
    public void testConnected() {
        TransparencyChecker c = new TransparencyCheckerImpl();
        HashSet<Exchange> exchanges = new HashSet<Exchange>();
        exchanges.add(from("1").to("2"));
        exchanges.add(from("2").to("3"));
        exchanges.add(from("3").to("1"));
        assertTrue(c.isTransparent(exchanges));
    }

    public void testDisconnected() {
        TransparencyChecker c = new TransparencyCheckerImpl();
        HashSet<Exchange> exchanges = new HashSet<Exchange>();
        exchanges.add(from("1").to("2"));
        exchanges.add(from("2").to("3"));
        exchanges.add(from("3").to("2"));
        assertFalse(c.isTransparent(exchanges));
    }
}
