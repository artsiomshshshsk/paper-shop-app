package com.artsiomshshshsk.mypapershopapp.service;

import com.artsiomshshshsk.mypapershopapp.controller.ComplaintController.ComplaintAction;
import com.artsiomshshshsk.mypapershopapp.exception.NotFoundException;
import com.artsiomshshshsk.mypapershopapp.model.ChatMessage;
import com.artsiomshshshsk.mypapershopapp.model.Complaint;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;
import com.artsiomshshshsk.mypapershopapp.repository.ChatMessageRepository;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public record ComplaintService(
        ComplaintRepository complaintRepository,
        OrderRepository orderRepository,
        ImageService imageService,
        ChatMessageRepository chatMessageRepository) {

    private static final String URL = "http://localhost:8080";
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images";


    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ComplaintResponse getComplaintById(Long complaintId) {
        return complaintRepository.findById(complaintId).map(this::toResponse).orElseThrow(() -> new NotFoundException("Complaint not found"));
    }

    public ComplaintResponse createComplaint(MultipartFile image, String description, Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        complaintRepository.findByOrderId(orderId).ifPresent(complaint -> {
            throw new IllegalArgumentException("Complaint for this order already exists");
        });

        var complaint = complaintRepository.save(new Complaint(LocalDate.now(), description, order));

        if (image != null) {
            if(!formatIsCorrect(Objects.requireNonNull(image.getOriginalFilename()))) {
                throw new IllegalArgumentException("Image must be in jpg, jpeg or png format");
            }

            var uploadDirectory = "src/main/resources/static/images";
            var imageId = imageService.saveImageToStorage(uploadDirectory, image);
            log.info("Image is present, sending it as first message to chat : {}", imageId);
            var chatMessage = new ChatMessage(null, false, imageId, complaint, LocalDateTime.now());
            chatMessageRepository.save(chatMessage);
        }
        return toResponse(complaint);
    }

    private boolean formatIsCorrect(String originalName) {
        return originalName.endsWith(".jpg") || originalName.endsWith(".jpeg") || originalName.endsWith(".png");
    }


    public void manageComplaint(Long complaintId, ComplaintAction action) {
        var complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("Complaint not found"));
        var order = complaint.getOrder();

        if(order.getOrderStatus() != OrderStatus.WAITING_FOR_REVIEW) {
            throw new IllegalArgumentException("You cannot manage complaint for this order");
        }

        order.setOrderStatus(action == ComplaintAction.APPROVE ? OrderStatus.COMPLAINT_APPROVED : OrderStatus.COMPLAINT_REJECTED);
        orderRepository.save(order);
    }

    public List<ChatMessageResponse> getChatMessages(Long complaintId) {
        var complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("Complaint not found"));
        return complaint.getMessages().stream().map(this::toResponse).toList();
    }

    public ChatMessageResponse sendMessage(Long complaintId, MultipartFile image, String message, Boolean sentToWorker) {
        var complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("Complaint not found"));

        if(image != null && !formatIsCorrect(Objects.requireNonNull(image.getOriginalFilename()))) {
            throw new IllegalArgumentException("Image must be in jpg, jpeg or png format");
        }

        var imageId = image != null ? imageService.saveImageToStorage(IMAGE_DIRECTORY, image) : null;
        var chatMessage = new ChatMessage(message, sentToWorker, imageId, complaint, LocalDateTime.now());
        return toResponse(chatMessageRepository.save(chatMessage));
    }

    public record ChatMessageResponse(Long id, String message, String imageUrl, Boolean toWorker, LocalDateTime timeSent) {}

    public record ComplaintResponse(Long complaintId, Long orderId, String complaintText, String status) {}

    private ChatMessageResponse toResponse(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getMessageId(),
                chatMessage.getMessageText(),
                chatMessage.getImageId() != null ? URL + "/images/" + chatMessage.getImageId() : null,
                chatMessage.getSendToWorker(),
                chatMessage.getTimeSent()
        );
    }

    private ComplaintResponse toResponse(Complaint complaint) {
        return new ComplaintResponse(complaint.getId(), complaint.getOrder().getId(), complaint.getDescription(), complaint.getOrder().getOrderStatus().toString());
    }
}
