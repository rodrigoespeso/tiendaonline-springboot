package com.dempo.service;

import com.dempo.dto.OrderDTO;
import com.dempo.exception.ServiceException;
import com.dempo.exception.UserNotFoundException;
import com.dempo.model.OrderEntity;
import com.dempo.repository.OrderRepository;
import com.dempo.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }
    
    // orders por usuario_id
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        logger.info("Buscando pedidos para el usuario con ID: {}", userId);
        
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            logger.warn("Usuario con ID {} no encontrado", userId);
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado");
        }
        
    	List<OrderEntity> entities = orderRepository.findByUserId(userId);
        if (entities.isEmpty()) {
            logger.warn("El usuario con ID {} no tiene pedidos", userId);
            throw new ServiceException("El usuario no tiene pedidos.");
        }
    	return entities.stream()
    		.map(OrderDTO::new)
    		.toList();
    }
}
