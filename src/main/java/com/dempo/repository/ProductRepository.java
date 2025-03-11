package com.dempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dempo.model.ProductEntity;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
	// productos de una categoria
    List<ProductEntity> findByCategoryId(Long categoryId);
}
