package com.steveday.pricing.server.config;

import com.steveday.pricing.pricefilters.PriceFilter;
import com.steveday.pricing.pricefilters.impl.CommissionPriceFilterImpl;
import com.steveday.pricing.publisher.CurrencyPairPriceGenerator;
import com.steveday.pricing.publisher.PricePublisher;
import com.steveday.pricing.publisher.impl.CurrencyPairPriceGeneratorImpl;
import com.steveday.pricing.publisher.impl.PricePublisherImpl;
import com.steveday.pricing.receiver.MessageListener;
import com.steveday.pricing.receiver.PriceCache;
import com.steveday.pricing.receiver.PriceProcessor;
import com.steveday.pricing.receiver.impl.MessageListenerImpl;
import com.steveday.pricing.receiver.impl.PriceCacheImpl;
import com.steveday.pricing.receiver.impl.PriceProcessorImpl;
import com.steveday.pricing.server.PricingServer;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@Configuration
@Profile(value = {"prod"})
public class PricingServerConfig {
    private Logger logger = LoggerFactory.getLogger(PricingServer.class);

    @Bean
    public Clock getClock() {
        return Clock.systemUTC();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.info("Pricing Server Started Successfully.");
        };
    }

    @Bean
    public PriceFilter priceFilter() {
        return new CommissionPriceFilterImpl();
    }

    @Bean
    public PriceCache priceCache() {
        return new PriceCacheImpl();
    }

    @Bean
    public CurrencyPairPriceGenerator priceGenerator() {
        return new CurrencyPairPriceGeneratorImpl();
    }

    @Bean
    public PricePublisher pricePublisher() {
        return new PricePublisherImpl();
    }

    @Bean
    public PriceProcessor priceProcessor() {
        return new PriceProcessorImpl();
    }

    @Bean
    public MessageListener messageListener() {
        return new MessageListenerImpl();
    }
}
