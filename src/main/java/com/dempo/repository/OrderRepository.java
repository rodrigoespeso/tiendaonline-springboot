package com.dempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dempo.model.OrderEntity;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	// pedidos de un usuario
    List<OrderEntity> findByUserId(Long userId);
}
