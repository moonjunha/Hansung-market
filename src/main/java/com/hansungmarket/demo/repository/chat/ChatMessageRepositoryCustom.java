package com.hansungmarket.demo.repository.chat;

import com.hansungmarket.demo.dto.chat.ChatMessageResponseDto;

import java.util.List;

public interface ChatMessageRepositoryCustom {
    List<ChatMessageResponseDto> findByChatRoomIdCustom(Long id);
}
