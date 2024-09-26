Feature: Stock Metrics

  Scenario: Calculate Dividend Yield for Common Stock
    When Dividend Yield is requested for "POP" , for Price 100 Pennies
    Then the result should be 0.08

  Scenario: Calculate Dividend Yield for Preferred Stock
    When Dividend Yield is requested for "GIN" , for Price 400 Pennies
    Then the result should be 0.5

  Scenario: Calculate P/E Ratio for Common Stock
    When PERatio for "ALE" , for Price 400 Pennies is requested
    Then the PEratio result should be 17.39
