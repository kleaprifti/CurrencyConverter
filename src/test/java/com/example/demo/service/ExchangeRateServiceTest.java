package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.configuration.ApiConfig;
import com.example.demo.dto.ExchangeRateResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


class ExchangeRateServiceTest {


    @Mock
    private RestTemplate restTemplate;
    @Mock
    private Logger logger;


    @Mock
    private ApiConfig apiConfig;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
     void testGetExchangeRate_HttpClientErrorException() {
        when(apiConfig.getKey()).thenReturn("valid_api_key");
        when(apiConfig.getExchangeRateApiUrl()).thenReturn("https://mockapi.example.com");
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateResponseDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        double rate = exchangeRateService.getExchangeRate("EUR", "USD");
        assertEquals(-1, rate);
        logger.info("testGetExchangeRate_HttpClientErrorException: Test successfully passed");

    }
    @Test
    void testGetExchangeRate_Error() {
        when(apiConfig.getExchangeRateApiUrl()).thenReturn("https://mockapi.example.com");

        when(restTemplate.getForObject(anyString(), eq(ExchangeRateResponseDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Call the method
        double rate = exchangeRateService.getExchangeRate("USD", "EUR");

        assertEquals(-1, rate);
    }

    @Test
     void testGetExchangeRate_Exception() {
        when(apiConfig.getKey()).thenReturn("valid_api_key");
        when(apiConfig.getExchangeRateApiUrl()).thenReturn("https://mockapi.example.com");
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateResponseDto.class)))
                .thenThrow(new RuntimeException("Something went wrong"));

        double rate = exchangeRateService.getExchangeRate("EUR", "USD");
        assertEquals(-1, rate);
        logger.info("testGetExchangeRate_Exception: Test successfully passed");

    }
    @Test
    void testGetExchangeRate_NullResponse() {
        when(apiConfig.getKey()).thenReturn("valid_api_key");
        when(apiConfig.getExchangeRateApiUrl()).thenReturn("https://mockapi.example.com");
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateResponseDto.class))).thenReturn(null);

        double rate = exchangeRateService.getExchangeRate("EUR", "USD");
        assertEquals(-1, rate);
        logger.info("testGetExchangeRate_NullResponse: Test successfully passed");
    }
    }