package com.GBCE.domain.fixture;

import com.GBCE.domain.Stock;
import com.GBCE.service.InstrumentService;

public class InstrumentServiceFixture {

    private final InstrumentService<Stock> instrumentService;

    public InstrumentServiceFixture(InstrumentService<Stock> instrumentService) {
        this.instrumentService = instrumentService;
    }

    public void populateInstruments() {
        StockDataFixture.prepareStocks().forEach(this.instrumentService::saveInstrument);
    }
}
