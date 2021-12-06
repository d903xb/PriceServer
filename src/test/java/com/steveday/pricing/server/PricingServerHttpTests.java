package com.steveday.pricing.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steveday.pricing.domain.Price;
import com.steveday.pricing.receiver.PriceCache;
import com.steveday.pricing.server.http.RestEndpointsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("junit")

@AutoConfigureMockMvc
class PricingServerHttpTests {

    @MockBean
    private Clock clock;

    @MockBean
    private PriceCache  mockPriceCache;

    @InjectMocks
    RestEndpointsImpl testEndpoints;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setMockOutput() {
    }

    @Test
    void testGetLatestPricesEndpoint() throws Exception {
        Collection<Price> priceList = new ArrayList<Price>();
        priceList.add(new Price("123", "EUR/USD'", new BigDecimal("0.12"), new BigDecimal("0.13"), LocalDateTime.of(2017,Month.FEBRUARY,3,6,30,40,50000)));
        when(mockPriceCache.getLatestPrices()).thenReturn(priceList);
        when(clock.instant()).thenReturn(Instant.ofEpochSecond(1000L));
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        String expectation = "[{\"uniqueId\":\"123\",\"instrumentName\":\"EUR/USD'\",\"bid\":0.12,\"ask\":0.13,\"timestamp\":\"2017-02-03 06:30:40\"}]";

        MvcResult result = this.mockMvc.perform(get("/prices/latest"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectation))
                .andReturn();

        //Print latest price out to console
        String response = result.getResponse().getContentAsString();
        System.out.println("Latest prices received from endpoint : " + response);
    }
}