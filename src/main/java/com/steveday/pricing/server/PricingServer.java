package com.steveday.pricing.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PricingServer {
    public static void main (final String[] pArgs) {
        SpringApplication.run(PricingServer.class, pArgs);
    }
}
