package com.example.demo.controller;

import com.example.demo.dto.ConversionRequestDto;
import com.example.demo.dto.ConversionResponseDto;
import com.example.demo.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyConversionController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @PostMapping("/convert")
    public ResponseEntity convertCurrency(@Valid @RequestBody ConversionRequestDto request) {
        double exchangeRate = exchangeRateService.getExchangeRate(request.getBaseCurrency(), request.getTargetCurrency());
        if (exchangeRate == -1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching exchange rate");
        }
        double convertedAmount = request.getAmount() * exchangeRate;

        ConversionResponseDto response = new ConversionResponseDto(
                request.getBaseCurrency(), request.getTargetCurrency(), request.getAmount(), convertedAmount
        );
        return ResponseEntity.ok(response);
    }
}
