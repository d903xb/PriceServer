# PriceServer Implementation by Steve Day (https://tinyurl.com/steve-day-cv)

JDK Version : 17

# To build and run tests:

mvn install

java -jar jarFileFromAbove

# To test REST endpoint

curl localhost:8080/prices/latest

# Approach

I wrote a Spring Boot server using the spring framework to autowire beans as required. To simulate the prices, I wrote a scheduled thread pool to output a CSV price quote for 3 currency pairs every second (currency pairs vary in each quote). To mimic a JMS receiver, I wrote a class with an onMessage method to parse the CSV quotes and update a ConcurrentHashMap (keyed by currency pair) with the quotes in real time as they are received. I used Spring Boot to provide a Restfull /prices/latest endpoint to retrieve the latest prices from the Hash Map when called. Finally I provided a unit test to test the REST layer that retreieves the latestPrices from the Hash Map and outputs them to the console after some assertions. I mocked out the latest price data  in the test to ensure the test runs consistently.

