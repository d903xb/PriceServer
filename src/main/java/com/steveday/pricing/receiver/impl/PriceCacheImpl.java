package com.steveday.pricing.receiver.impl;

import com.steveday.pricing.domain.Price;
import com.steveday.pricing.receiver.PriceCache;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class PriceCacheImpl implements PriceCache {

    //As we will have multiple threads hitting cache updating/retrieving prices, used concurrent hash map
    private ConcurrentHashMap<String, Price> priceCache = new ConcurrentHashMap<>();

    public void updatePrice(Price price) {
        priceCache.put(price.getInstrumentName(), price);
    }

    public Price getLatestPrice(String currencyPair) {
        return priceCache.get(currencyPair);
    }

    public Collection<Price> getLatestPrices() {
        return priceCache.values();
    }
}
