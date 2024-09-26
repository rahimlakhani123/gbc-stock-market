package com.GBCE.calculator;

import com.GBCE.domain.Stock;
import com.GBCE.service.InstrumentService;
import com.GBCE.service.StockNotFound;

public class StockMetricsCalculatorImpl implements StockMetricsCalculator {

    InstrumentService<Stock> instrumentService;

    public StockMetricsCalculatorImpl(InstrumentService<Stock> instrumentService) {
        this.instrumentService = instrumentService;
    }

    /**
     * Calculates Dividend Yield of the Stock
     * For Common Stock its LastDividend/Price For Preferred
     * Stock  its (Fixed Dividend .Par Value)/Price.
     * Price is in Pennies
     * 123.56 should be sent as 12356
     * 100.00 should be sent as 10000
     *
     * @param price
     * @return
     */
    public double dividendYield(String stockSymbol, long price) {
        priceCheck(price);

        var stockOpt = instrumentService.getInstrument(stockSymbol);

        return stockOpt.map(stock -> {
            if (stock.getStockType().equals(Stock.StockType.Common))
                return (double) stock.getLastDividend() / price;
            else
                return (double) (stock.getFixedDividend() * stock.getParValue()) / price;
        }).map(DoubleUtils::convertToTwoDps).orElseThrow(() -> new StockNotFound(stockSymbol));
    }

    /**
     * Calculates PERatio Its calculates using Price/Dividend Price is in Pennies
     * 123.56 should be sent as 12356
     * 100.00 should be sent as 10000
     *
     * @param price
     * @return
     */
    public double PERatio(String stockSymbol, long price) {
        priceCheck(price);

        var stockOpt = instrumentService.getInstrument(stockSymbol);
        return stockOpt.map(stock -> {
            if (stock.getLastDividend() == 0)
                return 0.0;
            return (double) price / stock.getLastDividend();
        }).map(DoubleUtils::convertToTwoDps).orElseThrow(() -> new StockNotFound(stockSymbol));
    }

    private void priceCheck(long price) {
        if (price < 0) {
            throw new IllegalArgumentException("Stock price cannot be negative");
        }
    }
}
