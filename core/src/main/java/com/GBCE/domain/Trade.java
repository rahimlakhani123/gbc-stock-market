package com.GBCE.domain;

import java.time.Instant;

public record Trade(long timestamp, long quantity, BuySell buySell, long price) {

    public Trade(long quantity, BuySell buySell, long price) {
        this(Instant.now().toEpochMilli(), quantity, buySell, price);
    }

    public static Trade onlyForTestingTradeFactory(long timestamp, long quantity, BuySell buySell, long price) {
        return new Trade(timestamp, quantity, buySell, price);
    }

    public enum BuySell {
        BUY, SELL
    }
}
