package com.example.proyectofinal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {

    @Value("${restaurante.precio.taquiza:110}")
    private BigDecimal taquizaPricePerPerson;

    @Value("${restaurante.precio.loza:500}")
    private BigDecimal tablewareCost;

    @Value("${restaurante.personas-por-bitrolero:10}")
    private int personsPerBitrolero;

    public BigDecimal getTaquizaPricePerPerson() {
        return taquizaPricePerPerson;
    }

    public BigDecimal getTablewareCost() {
        return tablewareCost;
    }

    public int getPersonsPerBitrolero() {
        return personsPerBitrolero;
    }

    public int calculateBitroleros(int numberOfPeople) {
        return (int) Math.ceil((double) numberOfPeople / personsPerBitrolero);
    }

    public BigDecimal calculateTaquizaTotal(int numberOfPeople) {
        return taquizaPricePerPerson.multiply(BigDecimal.valueOf(numberOfPeople));
    }

    public BigDecimal calculateMenuServiceTotal(int numberOfPeople, boolean includesTableware) {
        BigDecimal total = BigDecimal.ZERO;
        if (includesTableware) {
            total = total.add(tablewareCost);
        }
        return total;
    }
}
