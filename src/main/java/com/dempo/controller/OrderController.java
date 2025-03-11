package com.dempo.controller;

import com.dempo.dto.CategorySalesDTO;
import com.dempo.dto.Discount;
import com.dempo.dto.OrderDTO;
import com.dempo.dto.ProductBestSellerDTO;
import com.dempo.service.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
	private final OrderService orderService;
	private final OrderItemService orderItemService;

	public OrderController(OrderService orderService, OrderItemService orderItemService) {
		this.orderService = orderService;
		this.orderItemService = orderItemService;
    }
    
    // 1. Desglose de ventas por categoria
    @GetMapping("/categories/sales")
    public ResponseEntity<List<CategorySalesDTO>> getSalesByCategory() {
        List<CategorySalesDTO> salesData = orderItemService.getTotalSalesByCategory();
        return ResponseEntity.ok(salesData);
    }
	
    // 2. Pedidos de un usuario especifico
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable @NotNull @Min(1) Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    // 3. Producto mas vendido por categoría
    @GetMapping("/categories/best-sellers")
    public ResponseEntity<List<ProductBestSellerDTO>> getTopProductByCategory() {
    	List<ProductBestSellerDTO> topProducts = orderItemService.getBestSellingProductsByCategory();
        return ResponseEntity.ok(topProducts);
    }

}
