package com.steveday.pricing.receiver;

import com.steveday.pricing.domain.Price;

import java.util.Collection;

public interface PriceCache {
    void updatePrice(Price price);
    Price getLatestPrice(String currencyPair);
    Collection<Price> getLatestPrices();
}
