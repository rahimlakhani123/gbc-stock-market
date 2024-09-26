package com.GBCE.calculator;

import com.GBCE.domain.Stock;
import com.GBCE.domain.fixture.InstrumentServiceFixture;
import com.GBCE.service.InstrumentService;
import com.GBCE.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@DisplayName("Stock Metrics Calculator Test")
public class StockMetricsCalculatorTest {

    private static StockMetricsCalculator stockMetricsCalculator;

    @BeforeAll
    static void initialize() {
        InstrumentService<Stock> instrumentService = new StockService();
        InstrumentServiceFixture fixture = new InstrumentServiceFixture(instrumentService);
        fixture.populateInstruments();
        stockMetricsCalculator = new StockMetricsCalculatorImpl(instrumentService);
    }

    private static Stream<Arguments> produceStockDataPriceAndYield() {
        var stocks = List.of("TEA", "POP", "ALE", "GIN", "JOE");
        var prices = List.of(112, 132, 143, 2345, 6789);
        var yields = List.of(0, 0.06, 0.16, 0.09, 0.00);

        return IntStream.range(0, stocks.size())
                .mapToObj(i -> Arguments.of(stocks.get(i), prices.get(i), yields.get(i)));
    }

    private static Stream<Arguments> produceStockDataPriceAndPERatio() {
        var stocks = List.of("TEA", "POP", "ALE", "GIN", "JOE");
        var prices = List.of(10012, 10032, 10043, 2345, 6789);
        var peRatio = List.of(0, 1254.0, 436.65, 293.13, 522.23);

        return IntStream.range(0, stocks.size())
                .mapToObj(i -> Arguments.of(stocks.get(i), prices.get(i), peRatio.get(i)));
    }

    @ParameterizedTest
    @MethodSource("produceStockDataPriceAndYield")
    void dividendYield(String symbol, long price, double yield) {
        var calculatedYield = stockMetricsCalculator.dividendYield(symbol, price);
        Assertions.assertEquals(yield, calculatedYield, 0.001);
    }

    @ParameterizedTest
    @MethodSource("produceStockDataPriceAndPERatio")
    void PERatio(String symbol, long price, double peRatio) {
        var calculatedPERatio = stockMetricsCalculator.PERatio(symbol, price);
        Assertions.assertEquals(peRatio, calculatedPERatio, 0.001);
    }
}
