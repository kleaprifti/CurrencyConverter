package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversionRequestDto {
    @NotBlank
    private String baseCurrency;

    @NotBlank
    private String targetCurrency;

    @Positive
    private double amount;

}
