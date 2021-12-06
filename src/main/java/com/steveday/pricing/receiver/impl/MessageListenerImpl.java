package com.steveday.pricing.receiver.impl;

import com.steveday.pricing.domain.Price;
import com.steveday.pricing.receiver.MessageListener;
import com.steveday.pricing.receiver.PriceProcessor;
import com.steveday.pricing.server.PricingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MessageListenerImpl implements MessageListener {
    private final Logger logger = LoggerFactory.getLogger(PricingServer.class);

    @Autowired
    private PriceProcessor priceProcessor;

    public void onMessage(String message) {
        logger.info("RECEIVED-PRICES : " + message);
        Scanner scanner = new Scanner(message);
        Scanner dataScanner = null;
        while (scanner.hasNextLine()) {
            try {
                dataScanner = new Scanner(scanner.nextLine());
                dataScanner.useDelimiter(",");
                final Price parsedPrice = extractPriceFromCsv(dataScanner);
                priceProcessor.updatePrice(parsedPrice);
            } catch (Exception exception) {
                logger.error("Could not parse price from incoming feed : " + message);
            }
        }
        scanner.close();
    }

    private Price extractPriceFromCsv(Scanner dataScanner) {
        String uniqueId = null;
        String instrumentName = null;
        BigDecimal bid = null;
        BigDecimal ask = null;
        LocalDateTime timestamp = null;
        int index = 0;
        while (dataScanner.hasNext()) {
            String data = dataScanner.next();
            if (index == 0)
                uniqueId = data;
            else if (index == 1)
                instrumentName = data;
            else if (index == 2)
                bid = new BigDecimal(data);
            else if (index == 3)
                ask = new BigDecimal(data);
            else if (index == 4)
                timestamp  = LocalDateTime.parse(data);
            index++;
        }
        return new Price(uniqueId, instrumentName, bid, ask, timestamp);
    }
}
