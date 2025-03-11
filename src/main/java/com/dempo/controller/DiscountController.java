package com.dempo.controller;

import com.dempo.dto.Discount;
import com.dempo.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // 4. Obtener los descuentos mas altos desde una API externa
    @GetMapping("/top")
    public ResponseEntity<List<Discount>> getTopDiscounts() {
        List<Discount> discounts = discountService.getTopDiscounts();
        return ResponseEntity.ok(discounts);
    }
}
