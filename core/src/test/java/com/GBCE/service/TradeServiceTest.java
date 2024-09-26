package com.GBCE.service;

import com.GBCE.domain.Stock;
import com.GBCE.domain.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TradeServiceTest {

    private static TradeService tradeService;
    private static InstrumentService<Stock> instrumentService;

    @BeforeAll
    static void setUp() {
        instrumentService = new StockService();
        populateInstruments();
        tradeService = new TradeServiceImpl(instrumentService);
    }

    private static void populateInstruments() {
        var instruments = List.of(new Stock("TEA", Stock.StockType.Common, 0, 0, 100),
                new Stock("POP", Stock.StockType.Common, 8, 0, 100),
                new Stock("ALE", Stock.StockType.Common, 23, 0, 60),
                new Stock("GIN", Stock.StockType.Preferred, 8, 2, 100),
                new Stock("JOE", Stock.StockType.Common, 13, 0, 250));


        instruments.forEach(instrumentService::saveInstrument);
    }

    @Test
    public void testRecordTradeTransaction() {
        var timestamp = Instant.now().minusSeconds(60).toEpochMilli();
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(timestamp, 1000, Trade.BuySell.BUY, 6500));

        var timestamp2 = Instant.now().minusSeconds(2*60).toEpochMilli();
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(timestamp2, 1000, Trade.BuySell.SELL, 6500));

        var trades = tradeService.getTradeList("TEA");
        Assertions.assertEquals(2, trades.size());

        var timestamp3 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        tradeService.recordTradeTransaction("POP", Trade.onlyForTestingTradeFactory(timestamp3, 1000, Trade.BuySell.SELL, 6500));

        var trades2 = tradeService.getTradeList("POP");
        Assertions.assertEquals(1, trades2.size());
    }

}
