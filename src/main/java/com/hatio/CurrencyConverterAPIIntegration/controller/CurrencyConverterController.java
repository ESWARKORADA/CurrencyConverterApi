package com.hatio.CurrencyConverterAPIIntegration.controller;


import com.hatio.CurrencyConverterAPIIntegration.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService currencyConverterService;

    @GetMapping("/rates")
    public Map<String, Object> getConvertedRates(@RequestParam(defaultValue = "USD") String base){
        return currencyConverterService.getConvertedRates(base);
    }

    @PostMapping("/rates/convert")
    public Map<String, Object> getRequiredConvertedRates(@RequestBody Map<String, Object> requestBody){
        return currencyConverterService.getRequiredConvertedRates(requestBody);
    }

}
