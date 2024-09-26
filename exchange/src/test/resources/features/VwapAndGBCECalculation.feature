Feature: VWAP And GBCE Calculation
  Background:
    Given Below Trades are Booked in Exchange
      |SYMBOL| MINUTES_FROM_NOW |QUANTITY|BuySell|PRICE|
      | ALE  | 1                |   1000 | BUY   | 6500|
      | ALE  | 2                |   800  | SELL  | 6300|
      | ALE  | 0                |   2000 | BUY  |  8900|
      | ALE  | 8                |   1000 | BUY   | 6500|
      | GIN  | 0                |   1000 | BUY   | 6500|
      | GIN  | 0                |   800  | SELL  | 6300|
      | GIN  | 2                |   2000 | BUY  |  8900|
      | GIN  | 1                |   1000 | BUY   | 6500|


  Scenario: Calculate VWAP for ALE Trades in Last 5 minutes
    When VWAP is requested for "ALE"
    Then Expected VWAP is 7721.05

  Scenario: Calculate VWAP for GUN Trades in Last 5 minutes
    When VWAP is requested for "GUN"
    Then Expected VWAP is 7466.67

  Scenario: Calculate GBCE Index for Last 5 minutes
    When Index Is Requested
    Then Expected Index is 7592.79