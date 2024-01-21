package com.artsiomshshshsk.mypapershopapp;

import com.artsiomshshshsk.mypapershopapp.model.Order;
import com.artsiomshshshsk.mypapershopapp.model.OrderStatus;
import com.artsiomshshshsk.mypapershopapp.repository.ComplaintRepository;
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
    public CommandLineRunner demo(OrderRepository repository, ComplaintRepository complaintRepository) {
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

        };
    }

}
