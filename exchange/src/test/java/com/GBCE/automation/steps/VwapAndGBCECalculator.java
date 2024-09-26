package com.GBCE.automation.steps;

import com.GBCE.automation.hooks.GlobalHooks;
import com.GBCE.domain.Trade;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class VwapAndGBCECalculator {

    double vwap;
    double gbce;

    @Given("Below Trades are Booked in Exchange")
    public void belowTradesAreBookedInExchange(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String symbol = row.get("SYMBOL");
            int numberOfMinsInPast = Integer.parseInt(row.get("MINUTES_FROM_NOW"));
            long timestamp = LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).minus(numberOfMinsInPast, ChronoUnit.MINUTES).toEpochMilli();
            long email = Long.parseLong(row.get("QUANTITY"));
            Trade.BuySell buySell = Trade.BuySell.valueOf(row.get("BuySell"));
            long price = Long.parseLong(row.get("PRICE"));
            GlobalHooks.exchange.registerTrade(symbol, Trade.onlyForTestingTradeFactory(timestamp, email, buySell, price));
        }
    }

    @Then("Expected VWAP is {double}")
    public void expectedVWAPIs(double vwap) {
        Assertions.assertEquals(vwap,vwap, 0.01);
    }

    @When("VWAP is requested for {string}")
    public void vwapIsRequestedForALE(String symbol) {
        vwap = GlobalHooks.exchange.calculateVWAP(symbol);
    }

    @When("Index Is Requested")
    public void indexIsRequested() {
        gbce = GlobalHooks.exchange.calculateIndex();
    }

    @Then("Expected Index is {double}")
    public void expectedIndexIs(double result) {
        Assertions.assertEquals(result,gbce, 0.01);
    }
}
