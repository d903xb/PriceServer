package com.steveday.pricing.publisher.impl;

import com.steveday.pricing.publisher.CurrencyPairPriceGenerator;
import com.steveday.pricing.publisher.PricePublisher;
import com.steveday.pricing.receiver.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//Simulate a live price feed coming in over JMS, sends a message with 3 prices, every second.
public class PricePublisherImpl implements PricePublisher {

    @Autowired
    private MessageListener messageListener;

    @Autowired
    private CurrencyPairPriceGenerator currencyPairPriceGenerator;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public PricePublisherImpl() {
        simulatePricePublishing();
    }

    public void simulatePricePublishing() {
        Runnable pxGenerationTask = (()->{
            String quote1Csv = currencyPairPriceGenerator.generateCSVQuote();
            String quote2Csv = currencyPairPriceGenerator.generateCSVQuote();
            String quote3Csv = currencyPairPriceGenerator.generateCSVQuote();
            String message = quote1Csv + "," + quote2Csv + "," + quote3Csv;
            messageListener.onMessage(message);
        });
        scheduler.scheduleAtFixedRate(pxGenerationTask, 1, 1, TimeUnit.SECONDS);
    }
}
