package com.example.demo.service;

import com.example.demo.configuration.ApiConfig;
import com.example.demo.dto.ExchangeRateResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class ExchangeRateService {
    @Autowired
    private ApiConfig apiConfig;

    static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiConfig.getExchangeRateApiUrl())
                .queryParam("base", baseCurrency)
                .queryParam("symbols", targetCurrency)
                .queryParam("api_key", apiConfig.getKey());

        String url = builder.toUriString();

        try {
            ExchangeRateResponseDto response = restTemplate.getForObject(url, ExchangeRateResponseDto.class);
            if (response != null && response.getRates() != null) {
                return response.getRates().get(targetCurrency);
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error while fetching exchange rate: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error while fetching exchange rate: {}", e.getMessage());
        }

        return -1;
    }

}
