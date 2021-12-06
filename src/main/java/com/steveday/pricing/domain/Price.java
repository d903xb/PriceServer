package com.steveday.pricing.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class Price {
    private @Getter final String uniqueId;
    private @Getter final String instrumentName;
    private @Getter final BigDecimal bid;
    private @Getter final BigDecimal ask;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private @Getter final LocalDateTime timestamp;
}
