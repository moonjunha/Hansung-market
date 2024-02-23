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
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    @JsonIgnore
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    @JsonIgnore
    private User user2;

    @Builder
    private ChatRoom(Long id, User user1, User user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }
}
