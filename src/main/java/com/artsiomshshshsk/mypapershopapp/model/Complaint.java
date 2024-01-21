package com.artsiomshshshsk.mypapershopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "complaint_id")
        private Long id;

        @Column(name = "date_registered")
        private LocalDate dateRegistered;

        @Column(name = "description")
        private String description;

        @OneToOne
        @JoinColumn(name = "orderId")
        private Order order;

        @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<ChatMessage> messages;

        public Complaint(LocalDate dateRegistered, String description, Order order) {
                this.dateRegistered = dateRegistered;
                this.description = description;
                this.order = order;
        }
}
