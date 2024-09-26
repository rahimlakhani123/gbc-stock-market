package com.GBCE.calculator;

import com.GBCE.service.InstrumentService;

import java.util.Set;

import static com.GBCE.util.Constants.EPSILON;

public class GBCEAllShareIndex {
    private final InstrumentService instrumentService;
    private final VWAPCalculator vwapCalculator;

    public GBCEAllShareIndex(InstrumentService instrumentService, VWAPCalculator vwapCalculator) {
        this.instrumentService = instrumentService;
        this.vwapCalculator = vwapCalculator;
    }

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
