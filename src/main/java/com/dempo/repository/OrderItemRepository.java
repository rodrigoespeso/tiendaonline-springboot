package com.dempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dempo.dto.CategorySalesDTO;
import com.dempo.dto.ProductBestSellerDTO;
import com.dempo.model.OrderItemEntity;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
	
    // productos vendidos en una categoria
	List<OrderItemEntity> findByProductCategoryId(Long categoryId);
	
	// Hibernate instancia new com.dempo.dto.ProductBestSellerDTO
	// Si ser raw es menos trabajo para Hibernate, pero tendriamos que mapear en service

	@Query("""
			SELECT new com.dempo.dto.CategorySalesDTO(
				c.name, CAST(SUM(oi.quantity * oi.price) AS java.math.BigDecimal))
		       FROM OrderItemEntity oi
		       JOIN oi.product p 
		       JOIN p.category c
		       GROUP BY c.name
		       ORDER BY SUM(oi.quantity * oi.price) DESC 
			""")
	List<CategorySalesDTO> getTotalSalesByCategory();
		
	// Query nativa para poder usar DISTINC ON de PostgreSQL
	@Query(value = """
			SELECT DISTINCT ON (c.category_id) 
			       c.name AS category_name, 
			       p.name AS product_name,
			       SUM(oi.quantity) AS total_quantity_sold
			FROM ORDER_ITEMS oi 
			JOIN PRODUCTS p ON oi.product_id = p.product_id
			JOIN CATEGORIES c ON p.category_id = c.category_id
			GROUP BY c.category_id, c.name, p.product_id, p.name
			ORDER BY c.category_id, total_quantity_sold DESC;
		""", nativeQuery = true)
		List<Object[]> findBestSellingProductByCategoryNative();
	

}
