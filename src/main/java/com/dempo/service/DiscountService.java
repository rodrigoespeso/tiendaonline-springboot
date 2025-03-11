package com.dempo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dempo.dto.Discount;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private static final String DISCOUNTS_URL = "https://run.mocky.io/v3/569565df-3be0-44f5-8f00-d441355c2abe";

    public List<Discount> getTopDiscounts() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Discount[]> response = restTemplate.getForEntity(DISCOUNTS_URL, Discount[].class);

        if (response.getBody() == null || response.getBody().length == 0) {
            return List.of();
        }

        // Ordenar por porcentaje de descuento y devolver los 3 mayores
        return Arrays.stream(response.getBody())
                .sorted(Comparator.comparingDouble(Discount::getDiscountPercent)
                		.reversed())
                .limit(3)
                .toList();
    }
}
