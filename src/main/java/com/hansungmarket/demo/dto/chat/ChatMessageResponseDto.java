package com.hansungmarket.demo.dto.chat;

import com.hansungmarket.demo.entity.board.BoardImage;
import com.hansungmarket.demo.entity.chat.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String message;

    private Long userId;
    private String nickname;

    private LocalDateTime createdDateTime;

    @Builder
    private ChatMessageResponseDto(String message, Long userId, String nickname, LocalDateTime createdDateTime){
        this.message = message;
        this.userId = userId;
        this.nickname = nickname;
        this.createdDateTime = createdDateTime;
    }

    public ChatMessageResponseDto(ChatMessage entity) {
        this.message = entity.getMessage();
        this.userId = entity.getUser().getId();
        this.nickname = entity.getUser().getNickname();
        this.createdDateTime = entity.getCreatedDateTime();
    }
}
