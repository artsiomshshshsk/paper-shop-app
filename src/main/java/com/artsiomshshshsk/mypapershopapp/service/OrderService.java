package com.artsiomshshshsk.mypapershopapp.service;


import com.artsiomshshshsk.mypapershopapp.model.Order;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public record OrderService(OrderRepository orderRepository, ComplaintRepository complaintRepository) {
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> {
            var complaint = complaintRepository.findByOrderId(order.getId());
            return complaint.map(value -> toResponse(order, value.getId()))
                    .orElseGet(() -> toResponse(order, null));
        }).toList();
    }
    public record OrderResponse(Long orderId, OrderStatus status, LocalDate orderDate, Double price, Long complaintId) {}

    private OrderResponse toResponse(Order order, Long complaintId) {
        return new OrderResponse(order.getId(), order.getOrderStatus(), order.getOrderDate(), order.getPrice(), complaintId);
    }
}
