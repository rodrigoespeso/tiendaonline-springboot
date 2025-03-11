package com.dempo.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySalesDTO {
	@JsonProperty("category_name")
    private String categoryName;
	
	@JsonProperty("total_sales")
    private BigDecimal totalSales;
    
}


