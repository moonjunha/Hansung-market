package com.hansungmarket.demo.repository.chat;

import com.hansungmarket.demo.entity.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {
}
