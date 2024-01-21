package com.artsiomshshshsk.mypapershopapp.controller;


import com.artsiomshshshsk.mypapershopapp.service.ComplaintService;
import com.artsiomshshshsk.mypapershopapp.service.ComplaintService.ChatMessageResponse;
import com.artsiomshshshsk.mypapershopapp.service.ComplaintService.ComplaintResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Complaints", description = "Complaints API")
@RequestMapping("/complaints")
public record ComplaintController(ComplaintService complaintService) {

    @GetMapping
    @Operation(summary = "Get all complaints")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }


    @GetMapping("/{complaintId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    @Operation(summary = "Get a complaint by id")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable Long complaintId) {
        return ResponseEntity.ok(complaintService.getComplaintById(complaintId));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Complaint for this order already exists"),
            @ApiResponse(responseCode = "400", description = "Bad image format")
    })
    @Operation(summary = "Create a new complaint")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ComplaintResponse> createComplaint(@RequestParam(required=false, value="picture") MultipartFile image,
                                             @RequestParam(value="description") String description,
                                             @RequestParam(value="orderId") Long orderId) {
       return ResponseEntity.ok(complaintService.createComplaint(image, description, orderId));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "You can't manage this complaint"),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    @Operation(summary = "Manage a complaint")
    @PostMapping("/{complaintId}/manage")
    public ResponseEntity<Void> manageComplaint(@PathVariable Long complaintId, @RequestParam(value="action") ComplaintAction action) {
        complaintService.manageComplaint(complaintId, action);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{complaintId}/chat")
    @Operation(summary = "Get all chat messages for a complaint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable Long complaintId) {
        return ResponseEntity.ok(complaintService.getChatMessages(complaintId));
    }


    @Operation(summary = "Send a message to worker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Complaint not found"),
            @ApiResponse(responseCode = "400", description = "Bad image format")
    })
    @PostMapping(value = "/{complaintId}/chat/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatMessageResponse> sendMessageToWorker(@PathVariable Long complaintId,
                                                 @RequestParam(required=false, value="picture") MultipartFile image,
                                                 @RequestParam(value="toWorker") Boolean toWorker,
                                                 @RequestParam(value="message") String message) {
        return ResponseEntity.ok(complaintService.sendMessage(complaintId, image, message, toWorker));
    }


    public enum ComplaintAction {
        APPROVE,
        REJECT
    }

}
