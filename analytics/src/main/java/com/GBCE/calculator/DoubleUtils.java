package com.GBCE.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DoubleUtils {
    private DoubleUtils() {}

    static double convertToTwoDps(double price) {
        BigDecimal decimal = new BigDecimal(price);
        return decimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
