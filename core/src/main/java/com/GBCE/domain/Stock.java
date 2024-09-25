package com.GBCE.domain;

/**
 *
 */
public class Stock implements Instrument {

    private final String symbol;
    private final StockType stockType;

    private final long lastDividend; // in pennies
    private final long fixedDividend;
    private final int parValue;

    public Stock(String symbol, StockType stockType, long lastDividend, long fixedDividend,
                 int parValue) {
        this.symbol = symbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    public enum StockType {
        Common, Preferred
    }

    public long getLastDividend() {
        return lastDividend;
    }

    public StockType getStockType() {
        return stockType;
    }

    public long getFixedDividend() {
        return fixedDividend;
    }

    public int getParValue() {
        return parValue;
    }
}
