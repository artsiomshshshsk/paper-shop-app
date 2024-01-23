package com.artsiomshshshsk.mypapershopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id")
        private Long productId;

        @Column(name = "product_name")
        private String productName;

        @Column(name = "image_id")
        private String imageId;

        @Column(name = "description")
        private String description;

        @Column(name = "price")
        private double price;
}
