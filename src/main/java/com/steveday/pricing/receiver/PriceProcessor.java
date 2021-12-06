package com.steveday.pricing.receiver;

import com.steveday.pricing.domain.Price;

public interface PriceProcessor {
    void updatePrice(Price price);
}
