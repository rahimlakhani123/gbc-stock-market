package com.GBCE.calculator;

public interface StockMetricsCalculator {
    double dividendYield(String stockSymbol, long price);
    double PERatio(String stockSymbol, long price);
}
