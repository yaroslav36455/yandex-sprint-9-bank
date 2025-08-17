package by.tyv.exchange.model.dto;

import by.tyv.exchange.enums.CurrencyCode;

import java.math.BigDecimal;

public record ExchangeRateResponseDto(String title, BigDecimal rate, CurrencyCode value) { }
