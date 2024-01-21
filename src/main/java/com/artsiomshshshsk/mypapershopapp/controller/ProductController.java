package com.artsiomshshshsk.mypapershopapp.controller;


import com.artsiomshshshsk.mypapershopapp.service.ProductService;
import com.artsiomshshshsk.mypapershopapp.service.ProductService.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@Tag(name = "Products", description = "Products API")
@RequestMapping("/products")
public record ProductController(ProductService productService) {



    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }


    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad image format")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@RequestParam(required=false, value="picture") MultipartFile image,
                                               @RequestParam(value="name") String name,
                                               @RequestParam(value="description") String description,
                                               @RequestParam(value="price") Double price) {
        return ResponseEntity.ok(productService.createProduct(image, name, description, price));
    }

    @Operation(summary = "Delete a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Update a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Bad image format")
    })
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId,
                                               @RequestParam(required=false, value="picture") MultipartFile image,
                                               @RequestParam(required = false, value="name") String name,
                                               @RequestParam(required = false, value="description") String description,
                                               @RequestParam(required = false, value="price") Double price) {
        return ResponseEntity.ok(productService.updateProduct(productId, image, name, description, price));
    }
}
