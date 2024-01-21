package com.artsiomshshshsk.mypapershopapp.controller;

import com.artsiomshshshsk.mypapershopapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static com.artsiomshshshsk.mypapershopapp.service.OrderService.*;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Orders API")
public record OrderController(OrderService orderService) {

    @GetMapping
    @Operation(summary = "Get all orders")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}


