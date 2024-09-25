package com.GBCE.domain.fixture;

import com.GBCE.domain.Stock;

import java.util.List;

public class StockDataFixture {

    public static List<Stock> prepareStocks() {
        return List.of(new Stock("TEA", Stock.StockType.Common, 0, 0, 100),
                new Stock("POP", Stock.StockType.Common, 8, 0, 100),
                new Stock("ALE", Stock.StockType.Common, 23, 0, 60),
                new Stock("GIN", Stock.StockType.Preferred, 8, 2, 100),
                new Stock("JOE", Stock.StockType.Common, 13, 0, 250));
    }
}
