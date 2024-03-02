package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponseDto {
    private String baseCurrency;
    private String targetCurrency;
    private double originalAmount;
    private double convertedAmount;

}
