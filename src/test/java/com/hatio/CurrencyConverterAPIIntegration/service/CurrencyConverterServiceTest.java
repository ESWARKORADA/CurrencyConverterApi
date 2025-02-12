package com.hatio.CurrencyConverterAPIIntegration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyConverterService currencyConverterService;

    private final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=ba517c540d5b47bbf4d13f422fccda52";

    private Map<String, Object> mockApiResponse;

    @BeforeEach
    void setUp() {
        // Creating mock API Response
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.94);
        rates.put("INR", 83.0);

        mockApiResponse = new HashMap<>();
        mockApiResponse.put("rates", rates);
        mockApiResponse.put("base", "EUR");
    }

    @Test
    void testGetConvertedRates_Success() {
        when(restTemplate.getForEntity(API_URL, Map.class)).thenReturn(ResponseEntity.ok(mockApiResponse));

        Map<String, Object> response = currencyConverterService.getConvertedRates("USD");

        assertNotNull(response);
        assertEquals("USD", response.get("base"));
        assertTrue(((Map<String, Double>) response.get("rates")).containsKey("EUR"));
    }


    @Test
    void testGetConvertedRates_HttpClientErrorException() {
        when(restTemplate.getForEntity(API_URL, Map.class))
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.UNAUTHORIZED, "Unauthorized"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConverterService.getConvertedRates("USD");
        });

        assertTrue(exception.getMessage().contains("API request failed: 401"));
    }



    @Test
    void testGetConvertedRates_ResourceAccessException() {
        when(restTemplate.getForEntity(API_URL, Map.class))
                .thenThrow(new ResourceAccessException("Connection timeout"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConverterService.getConvertedRates("USD");
        });

        assertTrue(exception.getMessage().contains("Failed to connect to the API"));
    }


    @Test
    void testGetRequiredConvertedRates_Success() {
        when(restTemplate.getForEntity(API_URL, Map.class)).thenReturn(ResponseEntity.ok(mockApiResponse));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", "USD");
        requestBody.put("to", "INR");
        requestBody.put("amount", 100.0);

        Map<String, Object> response = currencyConverterService.getRequiredConvertedRates(requestBody);

        assertNotNull(response);
        assertEquals("USD", response.get("from"));
        assertEquals("INR", response.get("to"));
        assertEquals(100.0, response.get("amount"));
        assertNotNull(response.get("convertedAmount"));
    }

}
