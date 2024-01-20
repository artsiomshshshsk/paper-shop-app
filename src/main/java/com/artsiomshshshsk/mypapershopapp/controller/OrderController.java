package com.artsiomshshshsk.mypapershopapp.controller;

import com.artsiomshshshsk.mypapershopapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static com.artsiomshshshsk.mypapershopapp.service.OrderService.*;

@RestController
@RequestMapping("/orders")
public record OrderController(OrderService orderService) {

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}


