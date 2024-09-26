package com.GBCE.automation.steps;

import com.GBCE.automation.hooks.GlobalHooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class StockMetricsSteps {

    double yield;
    double peRatio;

    @When("Dividend Yield is requested for {string} , for Price {long} Pennies")
    public void dividendYield(String symbol , Long price) {
        yield = GlobalHooks.exchange.dividendYield(symbol, price);
    }


    @Then("the result should be {double}")
    public void theResultShouldBe(double result) {
        Assertions.assertEquals(result, yield, 0.001);
    }

    @When("PERatio for {string} , for Price {long} Pennies is requested")
    public void peRation(String symbol , Long price) {
        peRatio = GlobalHooks.exchange.calculatePERatio(symbol, price);
    }

    @Then("the PEratio result should be {double}")
    public void thePEratioResultShouldBe(double result) {
        Assertions.assertEquals(result, peRatio, 0.001);
    }
}
