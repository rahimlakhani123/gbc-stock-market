package com.GBCE.calculator;

import com.GBCE.domain.Trade;
import com.GBCE.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * VWAPCalculator - Volume Weighted Average Price
 */
public class VWAPCalculatorImpl implements VWAPCalculator {

    private static final Logger log = LoggerFactory.getLogger(VWAPCalculatorImpl.class);
    private final TradeService tradeService;

    public VWAPCalculatorImpl(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Override
    public double calculateVWAP(String symbol) {
        return calculateVWAP(symbol, Duration.ofMinutes(5));
    }

    /**
     * Calculated using  (Cumulative (Price * Volume) / (Cumulative Volume))
     * Computes to two Decimal places.
     * @param symbol
     * @param duration
     * @return
     */
    public double calculateVWAP(String symbol, Duration duration) {
        log.info("vwap requested for  {}", symbol);

        var trades = this.tradeService.getTradeList(symbol);
        var nowInEpoch = Instant.now().toEpochMilli();
        var windowsInMillis = duration.toMillis();

        var inscopeTrade = trades.stream()
                .filter(trade -> (nowInEpoch - trade.timestamp()) < windowsInMillis).toList();

        if(inscopeTrade.isEmpty()) {
            return 0.0;
        }

        log.info("Number of inscope trades{}", inscopeTrade.size());

        // this will create objects  - GC consideration,
        // for low memory utilization we could work with primitives
        // No NFRS are given so continuing with Streams for Elegance.
        var volumeAndPrice = inscopeTrade.stream()
                .mapToLong(trade -> (trade.price() * trade.quantity()))
                .sum();

        var summationOfVolumes = inscopeTrade.stream().mapToLong(Trade::quantity).sum();

        return DoubleUtils.convertToTwoDps((double) volumeAndPrice / summationOfVolumes);
    }
}
