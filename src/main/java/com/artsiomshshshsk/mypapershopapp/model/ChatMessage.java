package com.artsiomshshshsk.mypapershopapp.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "message_id")
        private Long messageId;

        @Column(name = "message_text")
        private String messageText;

        @Column(name = "is_pracownik")
        private Boolean isPracownik;

        @ManyToOne
        @JoinColumn(name = "complaintId")
        private Complaint complaint;
}
