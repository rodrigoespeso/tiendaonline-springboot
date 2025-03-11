package com.dempo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dempo.model.OrderEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
	@JsonProperty("order_id")
	private Long orderId;
	
	@JsonProperty("order_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime orderDate;
	
	@JsonProperty("status")
    private String status;
	
	@JsonProperty("total_sales")
    private BigDecimal totalSales;
    
	// constructor copia de entity
	public OrderDTO(OrderEntity e) {
		this.orderId = e.getId();
		this.orderDate = e.getOrderDate();
		this.status = e.getStatus();
		this.totalSales = e.getTotalAmount();
	}

}
