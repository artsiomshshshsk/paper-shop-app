package com.artsiomshshshsk.mypapershopapp.repository;

import com.artsiomshshshsk.mypapershopapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
