package com.hansungmarket.demo.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansungmarket.demo.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "message")
    private String message;

    @Column(name = "send_date")
    private LocalDateTime createdDateTime;

    @Builder
    private ChatMessage(Long id, ChatRoom chatRoom, User user, String message, LocalDateTime createdDateTime) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.user = user;
        this.message = message;
        this.createdDateTime = createdDateTime;
    }
}
