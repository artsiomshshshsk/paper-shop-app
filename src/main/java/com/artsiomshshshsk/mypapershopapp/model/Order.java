package com.artsiomshshshsk.mypapershopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "order_id")
        private Long id;

        @Column(name = "order_status")
        @Enumerated(EnumType.STRING)
        private OrderStatus orderStatus;

        @Column(name = "order_date")
        private LocalDate orderDate;

        @Column(name = "price")
        private Double price;
}
