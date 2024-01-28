package com.artsiomshshshsk.mypapershopapp.service;


import com.artsiomshshshsk.mypapershopapp.controller.ComplaintController;
import com.artsiomshshshsk.mypapershopapp.model.Complaint;
import com.artsiomshshshsk.mypapershopapp.model.Order;
import com.artsiomshshshsk.mypapershopapp.repository.ChatMessageRepository;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ComplaintService complaintService;



    @Test
    void getAllComplaints_ReturnsComplaintResponses() {
        // Arrange
        when(complaintRepository.findAll()).thenReturn(List.of(new Complaint(LocalDate.now(), "Test Complaint", createOrder())));

        // Act
        List<ComplaintService.ComplaintResponse> result = complaintService.getAllComplaints();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Complaint", result.get(0).complaintText());
    }

    @Test
    void getComplaintById_ExistingComplaint_ReturnsComplaintResponse() {
        // Arrange
        when(complaintRepository.findById(1L)).thenReturn(Optional.of(new Complaint(LocalDate.now(), "Test Complaint", createOrder())));

        // Act
        var result = complaintService.getComplaintById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Complaint", result.complaintText());
    }


    @Test
    void manageComplaint_ValidData_OrderStatusUpdated() {
        // Arrange
        Order order = new Order();
        order.setOrderStatus(OrderStatus.WAITING_FOR_REVIEW);
        when(complaintRepository.findById(1L)).thenReturn(Optional.of(new Complaint(LocalDate.now(), "Test Complaint", order)));

        // Act
        assertDoesNotThrow(() -> complaintService.manageComplaint(1L, ComplaintController.ComplaintAction.APPROVE));

        // Assert
        assertEquals(OrderStatus.COMPLAINT_APPROVED, order.getOrderStatus());
    }

    @Test
    void getChatMessages_ValidComplaintId_ReturnsChatMessageResponses() {
        // Arrange
        when(complaintRepository.findById(1L)).thenReturn(Optional.of(new Complaint(LocalDate.now(), "Test Complaint", createOrder())));

        // Act
        var result = complaintService.getChatMessages(1L);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }


    private Order createOrder() {
        return Order.builder()
                .orderStatus(OrderStatus.WAITING_FOR_REVIEW)
                .orderDate(java.time.LocalDate.now())
                .price(100.0)
                .build();
    }
}
