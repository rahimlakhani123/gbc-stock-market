package com.GBCE.automation.hooks;

import com.GBCE.exchange.Exchange;
import io.cucumber.java.Before;

public class GlobalHooks {

    public static Exchange exchange;
    private static boolean initialized = false;

    @Before
    public static void setUp() {
        if(!initialized) {
            exchange = new Exchange();
            exchange.bootstrap();
        }
    }
}
