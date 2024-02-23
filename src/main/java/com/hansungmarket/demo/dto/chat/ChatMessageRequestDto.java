package com.hansungmarket.demo.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {
    @NotBlank
    private Long chatRoomId;

    @NotBlank
    private Long receiverId;

    @NotBlank
    private String message;
}
