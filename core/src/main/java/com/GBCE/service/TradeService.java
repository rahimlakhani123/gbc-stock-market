package com.GBCE.service;

import com.GBCE.domain.Trade;

import java.util.List;

public interface TradeService {

    void recordTradeTransaction(String symbol, Trade trade);

    List<Trade> getTradeList(String symbol);
}
