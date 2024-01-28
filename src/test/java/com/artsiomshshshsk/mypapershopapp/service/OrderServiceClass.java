package com.artsiomshshshsk.mypapershopapp.service;

import com.artsiomshshshsk.mypapershopapp.model.Complaint;
import com.artsiomshshshsk.mypapershopapp.model.Order;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ComplaintRepository complaintRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllOrders_withComplaint() {
        // Arrange
        Order order = new Order(1L, OrderStatus.IN_PROGRESS, LocalDate.now(), 50.0);
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        Long complaintId = 100L;
        when(complaintRepository.findByOrderId(1L)).thenReturn(Optional.of(new Complaint(complaintId, LocalDate.now(), "Test Complaint", order, List.of())));

        // Act
        List<OrderService.OrderResponse> orderResponses = orderService.getAllOrders();

        // Assert
        assertEquals(1, orderResponses.size());
        OrderService.OrderResponse orderResponse = orderResponses.get(0);
        assertEquals(order.getId(), orderResponse.orderId());
        assertEquals(order.getOrderStatus(), orderResponse.status());
        assertEquals(order.getOrderDate(), orderResponse.orderDate());
        assertEquals(order.getPrice(), orderResponse.price());
        assertEquals(complaintId, orderResponse.complaintId());

        // Verify that appropriate methods were called
        verify(orderRepository, times(1)).findAll();
        verify(complaintRepository, times(1)).findByOrderId(1L);
    }

    @Test
    void getAllOrders_withoutComplaint() {
        // Arrange
        Order order = new Order(1L, OrderStatus.IN_PROGRESS, LocalDate.now(), 50.0);
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        when(complaintRepository.findByOrderId(1L)).thenReturn(Optional.empty());

        // Act
        List<OrderService.OrderResponse> orderResponses = orderService.getAllOrders();

        // Assert
        assertEquals(1, orderResponses.size());
        OrderService.OrderResponse orderResponse = orderResponses.get(0);
        assertEquals(order.getId(), orderResponse.orderId());
        assertEquals(order.getOrderStatus(), orderResponse.status());
        assertEquals(order.getOrderDate(), orderResponse.orderDate());
        assertEquals(order.getPrice(), orderResponse.price());
        assertEquals(null, orderResponse.complaintId());

        // Verify that appropriate methods were called
        verify(orderRepository, times(1)).findAll();
        verify(complaintRepository, times(1)).findByOrderId(1L);
    }
}
