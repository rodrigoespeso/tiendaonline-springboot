package com.dempo.service;

import com.dempo.dto.CategorySalesDTO;
import com.dempo.dto.ProductBestSellerDTO;
import com.dempo.model.OrderItemEntity;
import com.dempo.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
    
    // productos vendidos en una categoria
    public List<OrderItemEntity> getOrderItemsByCategory(Long categoryId) {
    	return orderItemRepository.findByProductCategoryId(categoryId);
    }
    
    public List<CategorySalesDTO> getTotalSalesByCategory(){
    	return orderItemRepository.getTotalSalesByCategory();    	

    }
    
    public List<ProductBestSellerDTO> getBestSellingProductsByCategory() {
        List<Object[]> results = orderItemRepository.findBestSellingProductByCategoryNative();
        
        return results.stream().map(obj -> 
            new ProductBestSellerDTO(
                String.valueOf(obj[0]),
                String.valueOf(obj[1]),
                Integer.valueOf(String.valueOf(obj[2]))) // chapuza
        ).toList();
    }
    
}
