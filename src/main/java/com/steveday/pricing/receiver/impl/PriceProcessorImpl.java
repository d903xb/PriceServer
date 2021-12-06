package com.steveday.pricing.receiver.impl;

import com.steveday.pricing.domain.Price;
import com.steveday.pricing.pricefilters.PriceFilter;
import com.steveday.pricing.receiver.PriceCache;
import com.steveday.pricing.receiver.PriceProcessor;
import com.steveday.pricing.server.PricingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PriceProcessorImpl implements PriceProcessor {
    private Logger logger = LoggerFactory.getLogger(PricingServer.class);

    @Autowired
    private PriceCache priceCache;

    @Autowired
    private PriceFilter commissionPriceFilter;

    public void updatePrice(Price price) {
        Price priceWithCommission = commissionPriceFilter.transformPrice(price);
        priceCache.updatePrice(price);
    }
}
