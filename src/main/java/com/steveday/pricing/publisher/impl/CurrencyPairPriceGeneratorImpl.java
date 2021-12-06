package com.steveday.pricing.publisher.impl;

import com.steveday.pricing.publisher.CurrencyPairPriceGenerator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//Simulate a live prices - generate random prices based off 5 currency pairs.
public class CurrencyPairPriceGeneratorImpl implements CurrencyPairPriceGenerator {
    @Autowired
    private Clock clock;
    private final List<String> currencyPairs = new ArrayList<String>();
    private final Random rn;

    public CurrencyPairPriceGeneratorImpl() {
        currencyPairs.add("EUR/GBP");
        currencyPairs.add("GBP/USD");
        currencyPairs.add("EUR/USD");
        currencyPairs.add("AUD/USD");
        currencyPairs.add("USD/CHF");
        rn = new Random();
    }

    public String generateCSVQuote() {
        int index = rn.nextInt(currencyPairs.size());
        String uniqId = UUID.randomUUID().toString();
        String currencyPair = currencyPairs.get(index);
        String bid = generatePrice();
        String ask = generatePrice();
        String timestamp = LocalDateTime.now(clock).toString();
        return uniqId + "," + currencyPair + "," + bid + "," + ask + "," + timestamp;
    }

    private String generatePrice(){
        DecimalFormat df2 = new DecimalFormat("0.0000");
        double min = 0.0000;
        double max = 2.1000;
        double rand = new Random().nextDouble();
        return df2.format(min + (rand * (max - min))) + "0";
    }
}
