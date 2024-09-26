package com.GBCE.calculator;

import com.GBCE.domain.Stock;
import com.GBCE.service.InstrumentService;

import java.util.Set;

import static com.GBCE.util.Constants.EPSILON;

/**
 * GBCE Index Calculator.
 * Leverage VWAP Calculator to calculate VWAPS for individual instruments
 * Then Finds the GM - Geometric Mean.
 */
public class GBCEAllShareIndex {
    private final InstrumentService<Stock> instrumentService;
    private final VWAPCalculator vwapCalculator;

    public GBCEAllShareIndex(InstrumentService<Stock> instrumentService, VWAPCalculator vwapCalculator) {
        this.instrumentService = instrumentService;
        this.vwapCalculator = vwapCalculator;
    }

    /**
     * Iterates over all Instruments Traded and Finds the geometric mean
     * Formats the value to 2 Decimal Place
     *
     * @return Double
     */
    public double calculateIndex() {
        Set<String> symbols = this.instrumentService.getAllSymbols();
        double product = 1.0;
        int count = 0;

        for (String symbol : symbols) {
            var vwap = vwapCalculator.calculateVWAP(symbol);
            if(Math.abs(vwap - 0.0) > EPSILON) {
                product *= vwapCalculator.calculateVWAP(symbol);
                count++;
            }
        }
        return DoubleUtils.convertToTwoDps(Math.pow(product, 1.0 / count));
    }
}
