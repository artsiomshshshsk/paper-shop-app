package com.artsiomshshshsk.mypapershopapp;

import com.artsiomshshshsk.mypapershopapp.model.Order;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;
import com.artsiomshshshsk.mypapershopapp.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyPaperShopAppApplication{

    public static void main(String[] args) {
        SpringApplication.run(MyPaperShopAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(OrderRepository repository) {
        return (args) -> {
            repository.save(Order.builder()
                    .orderStatus(OrderStatus.COMPLAINT_IN_PROGRESS)
                    .orderDate(java.time.LocalDate.now())
                    .price(100.0)
                    .build());

            repository.save(Order.builder()
                    .orderStatus(OrderStatus.COMPLAINT_COMPLETED)
                    .orderDate(java.time.LocalDate.now())
                    .price(200.0)
                    .build());

            repository.save(Order.builder()
                    .orderStatus(OrderStatus.COMPLAINT_CANCELLED)
                    .orderDate(java.time.LocalDate.now())
                    .price(300.0)
                    .build());

            repository.save(Order.builder()
                    .orderStatus(OrderStatus.RETURN_IN_PROGRESS)
                    .orderDate(java.time.LocalDate.now())
                    .price(400.0)
                    .build());
        };
    }

}
