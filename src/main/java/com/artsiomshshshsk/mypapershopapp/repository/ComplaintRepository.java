package com.artsiomshshshsk.mypapershopapp.repository;

import com.artsiomshshshsk.mypapershopapp.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByOrderId(Long orderId);
}
