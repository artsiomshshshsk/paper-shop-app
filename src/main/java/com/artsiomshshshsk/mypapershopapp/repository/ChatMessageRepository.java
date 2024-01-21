package com.artsiomshshshsk.mypapershopapp.repository;

import com.artsiomshshshsk.mypapershopapp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
