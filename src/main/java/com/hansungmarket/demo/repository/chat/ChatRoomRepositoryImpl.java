package com.hansungmarket.demo.repository.chat;

import com.hansungmarket.demo.entity.chat.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hansungmarket.demo.entity.chat.QChatRoom.chatRoom;

@RequiredArgsConstructor
@Repository
@Primary
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> findIdByUserId(Long userId) {
        return jpaQueryFactory.selectFrom(chatRoom)
                .where(chatRoom.user1.id.eq(userId)
                        .or(chatRoom.user2.id.eq(userId)))
                .innerJoin(chatRoom.user1).fetchJoin()
                .innerJoin(chatRoom.user2).fetchJoin()
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChatRoom> findIdByUsersId(Long userId1, Long userId2) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(chatRoom)
                .where(
                        (chatRoom.user1.id.eq(userId1).and(chatRoom.user2.id.eq(userId2)))
                                .or(
                                        (chatRoom.user1.id.eq(userId2).and(chatRoom.user2.id.eq(userId1)))
                                )
                )
                .fetchOne());
    }
}
