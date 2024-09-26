package com.GBCE.exchange;

import com.GBCE.calculator.*;
import com.GBCE.domain.Stock;
import com.GBCE.domain.Trade;
import com.GBCE.service.InstrumentService;
import com.GBCE.service.StockService;
import com.GBCE.service.TradeService;
import com.GBCE.service.TradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exchange {

    private static final Logger logger = LoggerFactory.getLogger(Exchange.class);
    private final InstrumentService<Stock> instrumentService;
    private final TradeService tradeService;
    private final VWAPCalculator vwapCalculator;
    private final GBCEAllShareIndex index;
    StockMetricsCalculator stockMetricsCalculator;
    ExecutorService pool = Executors.newFixedThreadPool(1);


    public Exchange() {
        instrumentService = new StockService();
        stockMetricsCalculator = new StockMetricsCalculatorImpl(instrumentService);
        tradeService = new TradeServiceImpl(instrumentService);
        vwapCalculator = new VWAPCalculatorImpl(tradeService);
        index = new GBCEAllShareIndex(instrumentService, vwapCalculator);
    }

    public void bootstrap() {
        try (InstrumentLoader loader = new InstrumentLoader(instrumentService)) {
            var loadingTask = pool.submit(loader);
            loadingTask.get();// Block Thread until
            logger.info("Number of Instruments Loaded {}", loadingTask.get());
        } catch (Exception e) {
            logger.error("Error Loading Instruments", e);
            System.exit(1);
        }
    }

    public double dividendYield(String stock, long price) {
        return stockMetricsCalculator.dividendYield(stock, price);
    }

    public double calculatePERatio(String symbol, long price) {
        return stockMetricsCalculator.PERatio(symbol, price);
    }

    public void registerTrade(String symbol, Trade trade) {
        tradeService.recordTradeTransaction(symbol, trade);
    }

    public double calculateVWAP(String stock) {
        return vwapCalculator.calculateVWAP(stock);
    }

    public double calculateIndex() {
        return index.calculateIndex();
    }
}
