package com.GBCE.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class to Round up Decimals
 */
public final class DoubleUtils {
    private DoubleUtils() {}

    /**
     * Rounds to Two Decimal with Rounding Methodology to Half Up.
     *
     * @param price
     * @return
     */
    static double convertToTwoDps(double price) {
        BigDecimal decimal = new BigDecimal(price);
        return decimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
