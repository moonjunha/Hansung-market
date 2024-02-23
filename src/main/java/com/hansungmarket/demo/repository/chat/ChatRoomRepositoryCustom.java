package com.hansungmarket.demo.repository.chat;

import com.hansungmarket.demo.dto.chat.ChatRoomDto;
import com.hansungmarket.demo.entity.chat.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {
    List<ChatRoom> findIdByUserId(Long userId);

    Optional<ChatRoom> findIdByUsersId(Long userId1, Long userId2);
}
