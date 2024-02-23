package com.hansungmarket.demo.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansungmarket.demo.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_notice")
public class ChatNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_Id")
    @JsonIgnore
    private ChatRoom chatRoom;

    @Builder
    private ChatNotice(Long id, User user, ChatRoom chatRoom) {
        this.id = id;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
