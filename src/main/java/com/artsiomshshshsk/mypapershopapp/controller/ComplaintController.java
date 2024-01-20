package com.artsiomshshshsk.mypapershopapp.controller;


import com.artsiomshshshsk.mypapershopapp.service.ComplaintService;
import com.artsiomshshshsk.mypapershopapp.service.ComplaintService.ComplaintResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public record ComplaintController(ComplaintService complaintService) {



    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }



    @RequestMapping("/{complaintId}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable Long complaintId) {
        return ResponseEntity.ok(complaintService.getComplaintById(complaintId));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createComplaint(@RequestParam(required=false, value="picture") MultipartFile image,
                                             @RequestParam(value="description") String description,
                                             @RequestParam(value="orderId") Long orderId) {

        return ResponseEntity.ok(complaintService.createComplaint(image, description, orderId));
    }




}
