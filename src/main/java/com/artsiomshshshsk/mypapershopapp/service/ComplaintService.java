package com.artsiomshshshsk.mypapershopapp.service;

import com.artsiomshshshsk.mypapershopapp.exception.NotFoundException;
import com.artsiomshshshsk.mypapershopapp.model.Complaint;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public record ComplaintService(ComplaintRepository complaintRepository, OrderRepository orderRepository) {


    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ComplaintResponse getComplaintById(Long complaintId) {
        return complaintRepository.findById(complaintId).map(this::toResponse).orElseThrow(() -> new NotFoundException("Complaint not found"));
    }

    public ComplaintResponse createComplaint(MultipartFile image, String description, Long orderId) {
//         send image to chat as a first message if image is present
//         todo

        var order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        complaintRepository.findByOrderId(orderId).ifPresent(complaint -> {
            throw new IllegalArgumentException("Complaint for this order already exists");
        });

        var complaint = complaintRepository.save(new Complaint(LocalDate.now(), description, order));
        return toResponse(complaint);
    }

    public record ComplaintResponse(Long complaintId, Long orderId, String complaintText) {}

    private ComplaintResponse toResponse(Complaint complaint) {
        return new ComplaintResponse(complaint.getId(), complaint.getOrder().getId(), complaint.getDescription());
    }
}
