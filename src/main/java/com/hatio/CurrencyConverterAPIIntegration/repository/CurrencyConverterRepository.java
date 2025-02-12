package com.hatio.CurrencyConverterAPIIntegration.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CurrencyConverterRepository {
    public Map<String, Object> getConvertedRates(String base);

    public Map<String, Object> getRequiredConvertedRates(Map<String, Object> requestBody);


}
