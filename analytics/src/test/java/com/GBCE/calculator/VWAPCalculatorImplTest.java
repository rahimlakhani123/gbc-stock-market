package com.GBCE.calculator;

import com.GBCE.domain.Stock;
import com.GBCE.domain.fixture.TradeServiceFixture;
import com.GBCE.service.InstrumentService;
import com.GBCE.service.StockService;
import com.GBCE.service.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VWAPCalculatorImplTest {

    private static TradeService tradeService;
    private static VWAPCalculator calculator;

    @BeforeAll
    static void setUp() {
        InstrumentService<Stock> instrumentService = new StockService();
        tradeService = TradeServiceFixture.buildTradeService(instrumentService);
        calculator = new VWAPCalculatorImpl(tradeService);
    }

    @Test
    void calculateVWAP() {
        var calculatedVWAP = calculator.calculateVWAP("TEA");
        double expectedVWAP = 7745.81;
        Assertions.assertEquals(expectedVWAP, calculatedVWAP, 0.01);
    }

    @Test
    void calculateVWAP_POP() {
        var calculatedVWAP = calculator.calculateVWAP("POP");
        double expectedVWAP = 0.0;
        Assertions.assertEquals(expectedVWAP, calculatedVWAP, 0.01);
    }
}