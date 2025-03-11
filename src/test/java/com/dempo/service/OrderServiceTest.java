package com.dempo.service;

import com.dempo.dto.OrderDTO;
import com.dempo.exception.ServiceException;
import com.dempo.exception.UserNotFoundException;
import com.dempo.model.OrderEntity;
import com.dempo.model.UserEntity;
import com.dempo.repository.OrderRepository;
import com.dempo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        // Opcional: Se ejecuta antes de cada test
    }

    @Test
    void testGetOrdersByUserId_whenExistingUser_thenReturnsOrders() {
        
    	// Given
    	Long userId = 1L;
    	Long orderId1 = 1L;
    	UserEntity ue = new UserEntity();
    	ue.setId(1L);
    	LocalDateTime date1 = LocalDateTime.of(2025, 1, 10, 10, 30);
    	LocalDateTime date2 = LocalDateTime.of(2025, 2, 20, 20, 30);
    	
    	OrderEntity oe1 = new OrderEntity(1L, ue, "pending", date1, BigDecimal.TEN);
    	OrderEntity oe2 = new OrderEntity(2L, ue, "delivered", date2, BigDecimal.TEN);
        List<OrderEntity> mockOrders = List.of(oe1, oe2);
        
        // When
        when(userRepository.existsById(userId)).thenReturn(true);
        when(orderRepository.findByUserId(userId)).thenReturn(mockOrders);

        List<OrderDTO> result = orderService.getOrdersByUserId(userId);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getOrderId());
        assertEquals("pending", result.get(0).getStatus());
        assertEquals(date1, result.get(0).getOrderDate());
        assertEquals(BigDecimal.TEN, result.get(0).getTotalSales());

        assertEquals(2L, result.get(1).getOrderId());
        assertEquals("delivered", result.get(1).getStatus());
        assertEquals(date2, result.get(1).getOrderDate());
        assertEquals(BigDecimal.TEN, result.get(1).getTotalSales());
        
        verify(userRepository, times(1)).existsById(userId);
        verify(orderRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetOrdersByUserId_whenUserIsNotFound_thenDoThrowsUserNotFoundException() {
        // Given
    	Long userId = 99L;
    	
    	// When
        when(userRepository.existsById(userId)).thenReturn(false);
        
        // Then
        assertThrows(UserNotFoundException.class, () -> orderService.getOrdersByUserId(userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(orderRepository, never()).findByUserId(anyLong());
    }

    @Test
    void testGetOrdersByUserId_whenUserGotNoOrders_thenDoThrowsServiceException() {
        
    	// Given
    	Long userId = 11L;
    	
    	// When
        when(userRepository.existsById(userId)).thenReturn(true);
        when(orderRepository.findByUserId(userId)).thenReturn(List.of());
        
        // Then
        assertThrows(ServiceException.class, () -> orderService.getOrdersByUserId(userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(orderRepository, times(1)).findByUserId(userId);
    }
    
    @Test
    void testGetOrdersByUserId_whenUnexpectedError_thenDoThrowsException() {
        // Given
    	Long userId = 500L;
    	String excMsg = "Error inesperado en el servicio";
    	
    	// WHen
        when(userRepository.existsById(userId)).thenReturn(true);
        when(orderRepository.findByUserId(userId)).thenThrow(new RuntimeException(excMsg));

        Exception exception = assertThrows(RuntimeException.class, () -> orderService.getOrdersByUserId(userId));
        
        // Then
        assertEquals(excMsg, exception.getMessage());

        verify(userRepository, times(1)).existsById(userId);
        verify(orderRepository, times(1)).findByUserId(userId);
    }

}
