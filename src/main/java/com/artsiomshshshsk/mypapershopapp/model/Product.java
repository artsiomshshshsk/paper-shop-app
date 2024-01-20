package com.artsiomshshshsk.mypapershopapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id")
        private Long productId;

        @Column(name = "product_name")
        private String productName;

        @Column(name = "image_url")
        private String imageUrl;

        @Column(name = "price")
        private BigDecimal price;
}
