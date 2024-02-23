package com.hansungmarket.demo.repository.chat;

import com.hansungmarket.demo.dto.chat.ChatMessageResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hansungmarket.demo.entity.chat.QChatMessage.chatMessage;

@RequiredArgsConstructor
@Repository
@Primary
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> findByChatRoomIdCustom(Long id) {
        return jpaQueryFactory.select(Projections.fields(ChatMessageResponseDto.class,
                    chatMessage.message,
                    chatMessage.user.id.as("userId"),
                    chatMessage.user.nickname,
                    chatMessage.createdDateTime))
                .from(chatMessage)
                .where(chatMessage.chatRoom.id.eq(id))
                .innerJoin(chatMessage.user)
                .orderBy(chatMessage.createdDateTime.asc())
                .fetch();
    }
}
