package com.artsiomshshshsk.mypapershopapp;

import com.artsiomshshshsk.mypapershopapp.model.Order;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;
import com.artsiomshshshsk.mypapershopapp.model.Product;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import com.artsiomshshshsk.mypapershopapp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class MyPaperShopAppApplication{

    public static void main(String[] args) {
        SpringApplication.run(MyPaperShopAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(OrderRepository repository, ComplaintRepository complaintRepository, ProductRepository productRepository) {
        return (args) -> {
            var order1 = repository.save(Order.builder()
                    .orderStatus(OrderStatus.WAITING_FOR_REVIEW)
                    .orderDate(java.time.LocalDate.now())
                    .price(100.0)
                    .build());

            var order2 = repository.save(Order.builder()
                    .orderStatus(OrderStatus.COMPLETED)
                    .orderDate(java.time.LocalDate.now())
                    .price(200.0)
                    .build());

            var order3 = repository.save(Order.builder()
                    .orderStatus(OrderStatus.IN_PROGRESS)
                    .orderDate(java.time.LocalDate.now())
                    .price(300.0)
                    .build());

            // make product

            var product1= productRepository.save(Product.builder()
                    .productName("product1")
                    .description("description1")
                    .imageId("yogurt.jpg")
                    .price(100.0)
                    .build());

        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5173", "http://127.0.0.1:5174", "http://127.0.0.1:5175")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

}
