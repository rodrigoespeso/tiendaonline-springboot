package com.dempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductBestSellerDTO {
	@JsonProperty("category_name")
    private String categoryName;
	
	@JsonProperty("product_name")
    private String productName;
	
    @JsonProperty("total_quantity_sold")
    private Integer totalQuantitySold;
    
}
