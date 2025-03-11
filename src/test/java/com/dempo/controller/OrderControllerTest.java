package com.dempo.controller;

import com.dempo.dto.OrderDTO;
import com.dempo.exception.ServiceException;
import com.dempo.exception.UserNotFoundException;
import com.dempo.service.DiscountService;
import com.dempo.service.OrderItemService;
import com.dempo.service.OrderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
	@MockBean
    private OrderService orderService;
    @MockBean
	private OrderItemService orderItemService;

    @Test
    void testGetOrdersByUser_whenValidUser_thenReturnsOk() throws Exception {
        // Given
        Long userId = 1L;
        List<OrderDTO> mockOrders = List.of(
                new OrderDTO(1L, LocalDateTime.of(2025, 1, 10, 10, 30), "pending", BigDecimal.TEN),
                new OrderDTO(2L, LocalDateTime.of(2025, 2, 20, 20, 30), "delivered", BigDecimal.TEN)
        );

        when(orderService.getOrdersByUserId(userId)).thenReturn(mockOrders);

        // WhenThen
        mockMvc.perform(get("/api/users/{userId}/orders", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].order_id").value(1))
                .andExpect(jsonPath("$[0].status").value("pending"))
                .andExpect(jsonPath("$[0].order_date").value("2025-01-10 10:30:00.000"))
                .andExpect(jsonPath("$[0].total_sales").value(10))
                .andExpect(jsonPath("$[1].order_id").value(2))
                .andExpect(jsonPath("$[1].status").value("delivered"))
                .andExpect(jsonPath("$[1].order_date").value("2025-02-20 20:30:00.000"))
                .andExpect(jsonPath("$[1].total_sales").value(10));
    }
    
    @Test
    void testGetOrdersByUser_UserNotFound_Returns404() throws Exception {
        // Given
        Long userId = 99L;
        when(orderService.getOrdersByUserId(userId)).thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When Then
        mockMvc.perform(get("/api/users/{userId}/orders", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }
    
    @Test
    void testGetOrdersByUser_UserWithoutOrders_Returns400() throws Exception {
        // Given
        Long userId = 11L;
        when(orderService.getOrdersByUserId(userId)).thenThrow(new ServiceException("El usuario no tiene pedidos."));

        // When Then
        mockMvc.perform(get("/api/users/{userId}/orders", userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El usuario no tiene pedidos."));
    }
    
    @Test
    void testGetOrdersByUser_InternalServerError_Returns500() throws Exception {
        // Given
        Long userId = 500L;
        when(orderService.getOrdersByUserId(userId)).thenThrow(new RuntimeException("Error inesperado en el servicio"));

        // When Then
        mockMvc.perform(get("/api/users/{userId}/orders", userId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ocurrió un error inesperado: Error inesperado en el servicio"));
    }
}
