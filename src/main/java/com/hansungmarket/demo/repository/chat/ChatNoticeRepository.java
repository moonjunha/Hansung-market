package com.hansungmarket.demo.repository.chat;

import com.hansungmarket.demo.entity.chat.ChatNotice;
import com.hansungmarket.demo.entity.chat.ChatRoom;
import com.hansungmarket.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatNoticeRepository extends JpaRepository<ChatNotice, Long> {
    void deleteByUserAndChatRoom(User user, ChatRoom chatRoom);

    void deleteByUser(User user);

    long countByUser(User user);

    long countByUserAndChatRoom(User user, ChatRoom chatRoom);

}
