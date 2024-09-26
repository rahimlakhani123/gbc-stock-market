package com.GBCE.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * @param timestamp
 * @param quantity
 * @param buySell
 * @param price
 * @param id
 */
public record Trade(long timestamp, long quantity, BuySell buySell, long price, String id) {

    public Trade(long quantity, BuySell buySell, long price) {
        this(Instant.now().toEpochMilli(), quantity, buySell, price, UUID.randomUUID().toString());
    }

    public static Trade onlyForTestingTradeFactory(long timestamp, long quantity, BuySell buySell, long price) {
        return new Trade(timestamp, quantity, buySell, price, UUID.randomUUID().toString());
    }

    public enum BuySell {
        BUY, SELL
    }
}
