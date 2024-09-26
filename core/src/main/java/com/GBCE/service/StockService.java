package com.GBCE.service;


import com.GBCE.domain.Stock;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Stock Service.
 */
public class StockService implements InstrumentService<Stock> {

    private final ConcurrentMap<String, Stock> stockMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Stock> getInstrument(String symbol) {
        return Optional.ofNullable(stockMap.get(symbol));
    }

    @Override
    public void saveInstrument(Stock stock) {
        stockMap.putIfAbsent(stock.getSymbol(), stock);
    }

    @Override
    public Set<String> getAllSymbols() {
        return stockMap.keySet();
    }
}
