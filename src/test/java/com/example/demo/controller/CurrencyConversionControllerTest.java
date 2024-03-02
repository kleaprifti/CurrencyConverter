package com.example.demo.controller;

import com.example.demo.dto.ConversionRequestDto;
import com.example.demo.dto.ConversionResponseDto;
import com.example.demo.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CurrencyConversionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionControllerTest.class);

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private CurrencyConversionController currencyController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testConvertCurrency_Success() {
        when(exchangeRateService.getExchangeRate("USD", "EUR")).thenReturn(0.85);

        ConversionRequestDto request = new ConversionRequestDto("USD", "EUR", 100);

        ResponseEntity responseEntity = currencyController.convertCurrency(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ConversionResponseDto response = (ConversionResponseDto) responseEntity.getBody();
        assertEquals(85.0, response.getConvertedAmount());
        logger.info("testConvertCurrency_Success: Test successfully passed");

    }

    @Test
     void testConvertCurrency_ErrorFetchingExchangeRate() {
        when(exchangeRateService.getExchangeRate(any(String.class), any(String.class))).thenReturn(-1.0);

        ConversionRequestDto request = new ConversionRequestDto("USD", "EUR", 100);

        ResponseEntity responseEntity = currencyController.convertCurrency(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error fetching exchange rate", responseEntity.getBody());
        logger.info("testConvertCurrency_ErrorFetchingExchangeRate: Test successfully passed");

    }
}