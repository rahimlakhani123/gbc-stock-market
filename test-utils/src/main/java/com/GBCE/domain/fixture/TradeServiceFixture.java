package com.GBCE.domain.fixture;

import com.GBCE.domain.Stock;
import com.GBCE.domain.Trade;
import com.GBCE.service.InstrumentService;
import com.GBCE.service.TradeService;
import com.GBCE.service.TradeServiceImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TradeServiceFixture {

    public static TradeService buildTradeService(InstrumentService<Stock> instrumentService) {
        populateInstruments(instrumentService);
        TradeService tradeService = new TradeServiceImpl(instrumentService);
        recordTrades(tradeService);
        return tradeService;
    }

    private static void populateInstruments(InstrumentService<Stock> instrumentService) {
        InstrumentServiceFixture fixture = new InstrumentServiceFixture(instrumentService);
        fixture.populateInstruments();
    }

    private static void recordTrades(TradeService tradeService) {
        recordTEATrade(tradeService, Instant.now());
        recordOutofScopePOPTrades(tradeService, Instant.now());
        recordGINTrade(tradeService, Instant.now());
        recordJOETrade(tradeService, Instant.now());
    }

    private static void recordJOETrade(TradeService tradeService, Instant now) {
        tradeService.recordTradeTransaction("JOE", Trade.onlyForTestingTradeFactory(now.minus(1, ChronoUnit.MINUTES).toEpochMilli(), 1000, Trade.BuySell.BUY, 6500));
        tradeService.recordTradeTransaction("JOE", Trade.onlyForTestingTradeFactory(now.minus(2, ChronoUnit.MINUTES).toEpochMilli(), 800, Trade.BuySell.BUY, 6300));
        tradeService.recordTradeTransaction("JOE", Trade.onlyForTestingTradeFactory(now.minus(3, ChronoUnit.MINUTES).toEpochMilli(), 2000, Trade.BuySell.SELL, 8900));
    }

    private static void recordGINTrade(TradeService tradeService, Instant now) {
        tradeService.recordTradeTransaction("GIN", Trade.onlyForTestingTradeFactory(now.minus(1, ChronoUnit.MINUTES).toEpochMilli(), 1000, Trade.BuySell.BUY, 900));
        tradeService.recordTradeTransaction("GIN", Trade.onlyForTestingTradeFactory(now.minus(2, ChronoUnit.MINUTES).toEpochMilli(), 800, Trade.BuySell.BUY, 1800));
        tradeService.recordTradeTransaction("GIN", Trade.onlyForTestingTradeFactory(now.minus(2, ChronoUnit.MINUTES).toEpochMilli(), 2000, Trade.BuySell.SELL, 1200));
        tradeService.recordTradeTransaction("GIN", Trade.onlyForTestingTradeFactory(now.toEpochMilli(), 75, Trade.BuySell.SELL, 1000));
        tradeService.recordTradeTransaction("GIN", Trade.onlyForTestingTradeFactory(now.minus(10, ChronoUnit.MINUTES).toEpochMilli(), 2000, Trade.BuySell.BUY, 1500));
    }

    private static void recordOutofScopePOPTrades(TradeService tradeService, Instant now) {
        tradeService.recordTradeTransaction("POP", Trade.onlyForTestingTradeFactory(now.minus(7, ChronoUnit.MINUTES).toEpochMilli(), 1000, Trade.BuySell.BUY, 6500));
        tradeService.recordTradeTransaction("POP", Trade.onlyForTestingTradeFactory(now.minus(8, ChronoUnit.MINUTES).toEpochMilli(), 800, Trade.BuySell.BUY, 6300));
        tradeService.recordTradeTransaction("POP", Trade.onlyForTestingTradeFactory(now.minus(9, ChronoUnit.MINUTES).toEpochMilli(), 2000, Trade.BuySell.SELL, 8900));
    }

    private static void recordTEATrade(TradeService tradeService, Instant now) {
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(now.minus(1, ChronoUnit.MINUTES).toEpochMilli(), 1000, Trade.BuySell.BUY, 6500));
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(now.minus(2, ChronoUnit.MINUTES).toEpochMilli(), 800, Trade.BuySell.BUY, 6300));
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(now.minus(2, ChronoUnit.MINUTES).toEpochMilli(), 2000, Trade.BuySell.SELL, 8900));
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(now.toEpochMilli(), 75, Trade.BuySell.SELL, 9000));
        tradeService.recordTradeTransaction("TEA", Trade.onlyForTestingTradeFactory(now.minus(10, ChronoUnit.MINUTES).toEpochMilli(), 2000, Trade.BuySell.BUY, 8900));
    }
}
