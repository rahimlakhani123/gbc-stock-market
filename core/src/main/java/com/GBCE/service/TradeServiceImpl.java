package com.GBCE.service;

import com.GBCE.domain.Stock;
import com.GBCE.domain.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;

public class TradeServiceImpl implements TradeService {

    private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);
    private final ConcurrentMap<String, List<Trade>> tradeMap = new ConcurrentHashMap<>();
    private final InstrumentService<Stock> instrumentService;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public TradeServiceImpl(InstrumentService<Stock> instrumentService) {
        this.instrumentService = instrumentService;
    }

    private BiFunction<String, List<Trade>, List<Trade>> persistTradeIfValidStock(
            Trade trade) {
        return (s, trades) -> {
            if (trades == null || trades.isEmpty()) {
                List<Trade> tradeList = new ArrayList<>();
                tradeList.add(trade);
                return tradeList;
            } else {
                trades.add(trade);
                return trades;
            }
        };
    }

    @Override
    public void recordTradeTransaction(String symbol, Trade trade) {
        var writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (instrumentService.getInstrument(symbol).isPresent())
                this.tradeMap.compute(symbol, persistTradeIfValidStock(trade));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Trade> getTradeList(String symbol) {
        List<Trade> tradeList = new ArrayList<>();
        var readLock = lock.readLock();
        readLock.lock();
        if (instrumentService.getInstrument(symbol).isPresent())
            tradeList = this.tradeMap.getOrDefault(symbol, new ArrayList<>());
        readLock.unlock();
        return tradeList;
    }
}
