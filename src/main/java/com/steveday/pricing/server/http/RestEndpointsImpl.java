package com.steveday.pricing.server.http;

import com.steveday.pricing.domain.Price;
import com.steveday.pricing.receiver.PriceCache;
import com.steveday.pricing.server.PricingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class RestEndpointsImpl {
    private Logger logger = LoggerFactory.getLogger(PricingServer.class);

    @Autowired
    private PriceCache priceCacheImpl;

    @GetMapping("/prices/latest")
    public Collection<Price> getLatestPrices() {
        final Collection<Price> latestPxCollection = priceCacheImpl.getLatestPrices();
        logger.info("HTTP-GET-RESPONSE : " + latestPxCollection.toString());
        return latestPxCollection;
    }
}
