package com.steveday.pricing.pricefilters;

import com.steveday.pricing.domain.Price;

public interface PriceFilter {
    Price transformPrice(Price px);
}
