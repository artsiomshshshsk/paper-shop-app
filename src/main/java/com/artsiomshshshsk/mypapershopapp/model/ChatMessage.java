package com.artsiomshshshsk.mypapershopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "chat_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "message_id")
        private Long messageId;

        @Column(name = "message_text")
        private String messageText;

        @Column(name = "is_worker")
        private Boolean sendToWorker;

        @Column(name = "image_id")
        private String imageId;

        @Column(name = "time_sent")
        private LocalDateTime timeSent;

        @ManyToOne
        @JoinColumn(name = "complaintId")
        private Complaint complaint;

        public ChatMessage(String messageText, Boolean sendToWorker, String imageId, Complaint complaint, LocalDateTime timeSent) {
                this.messageText = messageText;
                this.sendToWorker = sendToWorker;
                this.imageId = imageId;
                this.complaint = complaint;
                this.timeSent = timeSent;
        }
}
