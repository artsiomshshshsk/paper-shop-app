package com.artsiomshshshsk.mypapershopapp.service;

import com.artsiomshshshsk.mypapershopapp.exception.NotFoundException;
import com.artsiomshshshsk.mypapershopapp.model.Product;
import com.artsiomshshshsk.mypapershopapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ProductFound_ReturnsProductResponse() {
        // Arrange
        long productId = 1L;
        Product mockProduct = new Product(productId, "TestProduct", "imageId", "Description", 10.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        ProductService.ProductResponse result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        // Add more assertions based on your actual response structure
    }

    @Test
    void getProductById_ProductNotFound_ThrowsNotFoundException() {
        // Arrange
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void createProduct_ValidInput_ReturnsProductResponse() {
        // Arrange
        MockMultipartFile mockImage = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
        String name = "TestProduct";
        String description = "Description";
        Double price = 10.0;

        Product mockSavedProduct = new Product(1L, name, "imageId", description, price);
        when(productRepository.save(any(Product.class))).thenReturn(mockSavedProduct);

        // Act
        ProductService.ProductResponse result = productService.createProduct(mockImage, name, description, price);

        // Assert
        assertNotNull(result);
        // Add more assertions based on your actual response structure
    }
}
