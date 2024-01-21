package com.artsiomshshshsk.mypapershopapp.service;


import com.artsiomshshshsk.mypapershopapp.exception.NotFoundException;
import com.artsiomshshshsk.mypapershopapp.model.Product;
import com.artsiomshshshsk.mypapershopapp.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public record ProductService(ProductRepository productRepository, ImageService imageService) {

    private static final String URL = "https://localhost:8080";


    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id).map(this::toResponse).orElseThrow(()->new NotFoundException("Product not found"));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }


    public ProductResponse createProduct(MultipartFile image, String name, String description, Double price) {
        var product = new Product();

        productImage(image, product);

        try {
            product.setProductName(Objects.requireNonNull(name, "Name must not be null"));
            product.setDescription(Objects.requireNonNull(description, "Description must not be null"));
            product.setPrice(Objects.requireNonNull(price, "Price must not be null"));
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return toResponse(productRepository.save(product));
    }


    private boolean formatIsCorrect(String originalName) {
        return originalName.endsWith(".jpg") || originalName.endsWith(".jpeg") || originalName.endsWith(".png");
    }

    public void deleteProduct(Long productId) {
        productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.deleteById(productId);
    }

    public ProductResponse updateProduct(Long productId, MultipartFile image, String name, String description, Double price) {

        var product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        productImage(image, product);
        Optional.ofNullable(name).ifPresent(product::setProductName);
        Optional.ofNullable(description).ifPresent(product::setDescription);
        Optional.ofNullable(price).ifPresent(product::setPrice);
        return toResponse(productRepository.save(product));
    }

    private void productImage(MultipartFile image, Product product) {
        Optional.of(image).map(img -> {
            if(!formatIsCorrect(Objects.requireNonNull(img.getOriginalFilename()))) {
                throw new IllegalArgumentException("Image must be in jpg, jpeg or png format");
            }

            var uploadDirectory = "src/main/resources/static/images";
            var imageId = imageService.saveImageToStorage(uploadDirectory, img);
            product.setImageId(imageId);
            return null;
        });
    }

    public record ProductResponse(Long id, String name, String imageUrl,String description, Double price) {}

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getProductId(),
                product.getProductName(),
                product.getImageId() != null ? URL + "/images/" + product.getImageId() : null,
                product.getDescription(),
                product.getPrice());
    }

}
