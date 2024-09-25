package com.GBCE.calculator;

import com.GBCE.domain.fixture.InstrumentServiceFixture;
import com.GBCE.domain.fixture.TradeServiceFixture;
import com.GBCE.service.InstrumentService;
import com.GBCE.service.StockService;
import com.GBCE.service.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.GBCE.domain.Stock;

class GBCEAllShareIndexTest {

    GBCEAllShareIndex index;

    @BeforeEach
    void setUp() {
        InstrumentService<Stock> instrumentService = new StockService();
        TradeService tradeService = TradeServiceFixture.buildTradeService(instrumentService);
        VWAPCalculator calculator = new VWAPCalculatorImpl(tradeService);
        index = new GBCEAllShareIndex(instrumentService, calculator);
    }

    @Test
    void calculateIndex() {
        var indexVal = index.calculateIndex();
        double expectedVal = 4204.256;
        Assertions.assertEquals(indexVal, expectedVal, 0.001);
    }
}