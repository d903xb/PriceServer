package com.steveday.pricing.pricefilters.impl;

import com.steveday.pricing.domain.Price;
import com.steveday.pricing.pricefilters.PriceFilter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CommissionPriceFilterImpl implements PriceFilter {
    private final MathContext mathContext = new MathContext(4);
    private final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private final BigDecimal COMMISSION_PERCENT = new BigDecimal("0.1");
    private final BigDecimal COMMISSION_DECIMAL = COMMISSION_PERCENT.divide(ONE_HUNDRED, 10, RoundingMode.HALF_UP);

    public Price transformPrice(Price px) {
        try {
            return addSubtractCommission(px);
        } catch (Exception exception) {
            return px;
        }
    }

    private Price addSubtractCommission(Price px) {
        BigDecimal ask = px.getAsk();
        BigDecimal bid = px.getBid();

        BigDecimal askCommission = ask.multiply(COMMISSION_DECIMAL, mathContext);
        BigDecimal bidCommission = bid.multiply(COMMISSION_DECIMAL, mathContext);

        return new Price(px.getUniqueId(), px.getInstrumentName(), bid.subtract(bidCommission), ask.add(askCommission), px.getTimestamp());
    }
}
